package be.alb_mar_hen.servlet;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.alb_mar_hen.daos.MachineDAO;
import be.alb_mar_hen.daos.MaintenanceDAO;
import be.alb_mar_hen.enumerations.MachineStatus;
import be.alb_mar_hen.enumerations.MaintenanceStatus;
import be.alb_mar_hen.models.Employee;
import be.alb_mar_hen.models.Machine;
import be.alb_mar_hen.models.Maintenance;
import be.alb_mar_hen.validators.NumericValidator;
import be.alb_mar_hen.validators.ObjectValidator;

/**
 * Servlet implementation class MaintenanceResponsableServlet
 */
public class MaintenanceResponsableDashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String VALIDATE_ACTION = "Validate";
	private static final String RESTART_MAINTENANCE_ACTION = "Restart maintenance";
	
	private final MachineDAO machineDAO = new MachineDAO();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MaintenanceResponsableDashboardServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		ObjectValidator objValidator = new ObjectValidator();
		
		Employee employee = (Employee) session.getAttribute("employee");
		
		if (!objValidator.hasValue(employee)) {
			request.setAttribute("errorMessage", "Access denied. Please log in to access this page.");
			request.getRequestDispatcher("/WEB-INF/jsps/Authentication.jsp").forward(request, response);
			return;
		}
				
		Collection<Machine> machines = machineDAO.findAll_terry();
		
		if (machines.isEmpty()) {
			request.setAttribute("errorMessage", "No machines found.");
			request.getRequestDispatcher("/WEB-INF/jsps/Authentication.jsp").forward(request, response);
			return;
		}
				
		request.setAttribute("machines", machines);
		request.getRequestDispatcher("/WEB-INF/jsps/MaintenanceResponsableDashboard.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO: Méthode static dans model Machine pour récupérer toutes les machines au lieu d'appeler le DAO Machine
		ObjectValidator objValidator = new ObjectValidator();
		NumericValidator numValidator = new NumericValidator();
		
	    String machineIdParam = request.getParameter("machineId");
	    String actionParam = request.getParameter("action");	    

	    // Validation des paramètres
	    String errorMessage = validateParameters(machineIdParam, actionParam, objValidator);
	    if (objValidator.hasValue(errorMessage)) {
	    	System.out.println("errorMessage doPost: " + errorMessage);
	        forwardWithError(request, response, errorMessage);
	        return;
	    }

	    // Conversion et validation de l'ID de la machine
	    int machineId = parseMachineId(machineIdParam, request, response);
	    if (!numValidator.isPositive(machineId)) {
			System.out.println("machineId: " + machineId);
	    	return;
	    }

	    // Vérification de l'existence et du statut de la machine
	    Machine machineToUpdate = machineDAO.find(machineId);
	    System.out.println("machineToUpdate: " + machineToUpdate);	    
	    if (
    		!objValidator.hasValue(objValidator) || 
    		!machineToUpdate.getStatus().equals(MachineStatus.NEED_VALIDATION)
	    ) {
	        forwardWithError(
        		request, 
        		response, 
        		!objValidator.hasValue(machineToUpdate) 
        			? "Machine not found." 
    				: "Machine already validated."
	        );
	        return;
	    }

	    System.out.println("ici");
	    // Mise à jour des statuts selon l'action
	    Maintenance maintenanceToUpdate = machineToUpdate.getLastMaintenance();
	    if (VALIDATE_ACTION.equals(actionParam)) {
	    	machineToUpdate.setStatus(MachineStatus.OK);
	    	
	    	maintenanceToUpdate.setStatus(MaintenanceStatus.DONE);
	    	
		} else if (RESTART_MAINTENANCE_ACTION.equals(actionParam)) {
			maintenanceToUpdate.setStatus(MaintenanceStatus.IN_PROGRESS);
			maintenanceToUpdate.setEndDateTime(Optional.of(LocalDateTime.now()));
			
			machineToUpdate.setStatus(MachineStatus.IN_MAINTENANCE);
		}
	    System.out.println("machineToUpdate: " + machineToUpdate);
	    System.out.println("maintenanceToUpdate: " + maintenanceToUpdate);

	    // Mise à jour en base de données
	    if (!updateMachineAndMaintenanceInDatabase(machineToUpdate, maintenanceToUpdate, request, response)) 
	    	return;

	    System.out.println("Machine " + machineToUpdate + " status updated successfully.");
	    request.setAttribute("successMessage", "Machine " + machineToUpdate.getId().get() + " status updated successfully.");
	    doGet(request, response);
	}

	private String validateParameters(String machineIdParam, String action, ObjectValidator objValidator) {
	    if (!objValidator.hasValue(action)) 
	    	return "Action is required.";
	    
	    if (
    		!VALIDATE_ACTION.equals(action) && 
    		!RESTART_MAINTENANCE_ACTION.equals(action)
		) 
	    	return "Invalid action.";
	    
	    if (!objValidator.hasValue(machineIdParam)) 
	    	return "Machine ID is required.";
	    
	    return null;
	}

	private int parseMachineId(
		String machineIdParam, 
		HttpServletRequest request,
		HttpServletResponse response
	) throws ServletException, IOException 
	{
	    try {
	        int machineId = Integer.parseInt(machineIdParam);
	        if (machineId <= 0) 
	        	throw new NumberFormatException();
	        return machineId;
	    } catch (NumberFormatException e) {
	        forwardWithError(request, response, "Machine ID must be a positive number.");
	        return -1;
	    }
	}

	private boolean updateMachineAndMaintenanceInDatabase(
		Machine machine,
		Maintenance maintenance,
		HttpServletRequest request,
		HttpServletResponse response
	) throws ServletException, IOException 
	{
		boolean isMachineUpdated = machine.updateInDatabase(machineDAO); 
		boolean isMaintenanceUpdated = maintenance.updateInDatabase(new MaintenanceDAO());
	    if (!isMachineUpdated || !isMaintenanceUpdated) {
	        forwardWithError(
        		request, 
        		response, 
        		"Failed to update " 
        		+ (isMachineUpdated ? "maintenance" : "machine") 
        		+ " status."
    		);
	        System.out.println("Failed to update " + (isMachineUpdated ? "maintenance" : "machine") + " status.");
	        return false;
	    }
	    
	    System.out.println("Machine " + machine + " updated successfully.");
	    return true;
	}

	private void forwardWithError(
		HttpServletRequest request, 
		HttpServletResponse response,
		String errorMessage
	) throws ServletException, IOException 
	{
	    request.setAttribute("errorMessage", errorMessage);
	    doGet(request, response);
	}

}
