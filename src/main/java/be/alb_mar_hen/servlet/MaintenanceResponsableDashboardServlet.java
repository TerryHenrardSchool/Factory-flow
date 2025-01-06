package be.alb_mar_hen.servlet;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.alb_mar_hen.daos.MachineDAO;
import be.alb_mar_hen.daos.MaintenanceDAO;
import be.alb_mar_hen.daos.MaintenanceWorkerDAO;
import be.alb_mar_hen.enumerations.MachineStatus;
import be.alb_mar_hen.enumerations.MaintenanceStatus;
import be.alb_mar_hen.models.Employee;
import be.alb_mar_hen.models.Machine;
import be.alb_mar_hen.models.Maintenance;
import be.alb_mar_hen.models.MaintenanceResponsable;
import be.alb_mar_hen.models.MaintenanceWorker;
import be.alb_mar_hen.validators.DateValidator;
import be.alb_mar_hen.validators.NumericValidator;
import be.alb_mar_hen.validators.ObjectValidator;
import be.alb_mar_hen.validators.StringValidator;

/**
 * Servlet implementation class MaintenanceResponsableServlet
 */
public class MaintenanceResponsableDashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String VALIDATE_ACTION = "Validate";
	private static final String RESTART_MAINTENANCE_ACTION = "Restart maintenance";
	private static final String SEND_TO_MAINTENANCE_ACTION = "Send to maintenance";
	
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
		Employee employee = (Employee) session.getAttribute("employee");
		
		ObjectValidator objValidator = new ObjectValidator();
		
		if (!objValidator.hasValue(employee)) {
			request.setAttribute("errorMessage", "Access denied. Please log in to access this page.");
			request.getRequestDispatcher("/WEB-INF/jsps/Authentication.jsp").forward(request, response);
			return;
		}
		
		Collection<String> errorMessages = new ArrayList<>();
		
		String postErrorMessage = (String) request.getAttribute("errorMessage");
		if (objValidator.hasValue(postErrorMessage)) {
            errorMessages.add(postErrorMessage);
		}
		
		System.out.println("error messages: " + errorMessages);
		
		Collection<Machine> machines = machineDAO.findAll_terry();
		if (machines.isEmpty()) {
			errorMessages.add("No machines found.");
		}
		
		Collection<MaintenanceWorker> maintenanceWorkers = MaintenanceWorker.getAllFromDatabase(new MaintenanceWorkerDAO());
		if (maintenanceWorkers.isEmpty()) {
			errorMessages.add("No maintenance workers found.");
		}
		
		request.setAttribute("maintenanceWorkers", maintenanceWorkers);
		request.setAttribute("machines", machines);
		request.setAttribute("errorMessages", errorMessages);
		request.getRequestDispatcher("/WEB-INF/jsps/MaintenanceResponsableDashboard.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO: Méthode static dans model Machine pour récupérer toutes les machines au lieu d'appeler le DAO Machine
		ObjectValidator objValidator = new ObjectValidator();
		NumericValidator numValidator = new NumericValidator();
		
		HttpSession session = request.getSession();
		MaintenanceResponsable authenticatedMaintenanceResponsable = 
			(MaintenanceResponsable) session.getAttribute("employee");
		
		if (!objValidator.hasValue(authenticatedMaintenanceResponsable)) {
			request.setAttribute("errorMessage", "Access denied. Please log in to access this page.");
			request.getRequestDispatcher("/WEB-INF/jsps/Authentication.jsp").forward(request, response);
			return;
		}
		
	    String machineIdParam = request.getParameter("machineId");
	    String actionParam = request.getParameter("action");
	    String[] selectedWorkerIds = request.getParameterValues("workerIds");

	    // Validation des paramètres
	    String errorMessage = validateParameters(machineIdParam, actionParam, selectedWorkerIds, objValidator);
	    if (objValidator.hasValue(errorMessage)) {
	    	System.out.println("Invalid paramater Error message: " + errorMessage);
	        forwardWithError(request, response, errorMessage);
	        return;
	    }

	    // Conversion et validation de l'ID de la machine
	    int machineId = parseMachineId(machineIdParam, request, response);
	    if (!numValidator.isPositive(machineId)) {
	    	return;
	    }

	    // Vérification de l'existence et du statut de la machine
	    Machine machineToUpdate = machineDAO.find(machineId);
	    if (!objValidator.hasValue(machineToUpdate)) {
	        forwardWithError(
        		request, 
        		response, 
        		!objValidator.hasValue(machineToUpdate) 
        			? "Machine not found." 
    				: "Machine already validated."
	        );
	        return;
	    }

	    // Mise à jour des statuts selon l'action
	    String successMessage = "";
	    System.out.println("Action: ");
	    Maintenance maintenanceToUpdate = machineToUpdate.getLastMaintenance();
	    if (actionParam.equals(VALIDATE_ACTION) || actionParam.equals(RESTART_MAINTENANCE_ACTION)) {
	    	processValidationAction(machineToUpdate, maintenanceToUpdate, actionParam, request, response);
	    	successMessage = "Machine " + machineToUpdate.getId().get() + " status updated successfully.";
	    	
	    } else if (actionParam.equals(SEND_TO_MAINTENANCE_ACTION)) {
	    	System.out.println("SEND_TO_MAINTENANCE_ACTION");
	    	processSendToMaintenanceAction(
    			machineToUpdate,
    			maintenanceToUpdate,
    			authenticatedMaintenanceResponsable,
    			selectedWorkerIds, 
    			objValidator,
    			request, 
    			response
			);
	    	successMessage = "Machine " + machineToUpdate.getId().get() + " sent to maintenance successfully.";
		}
		
	    System.out.println("success message" + successMessage);
		request.setAttribute("successMessage", successMessage);
    	doGet(request, response);
	}
	
	private void processSendToMaintenanceAction(
		Machine machineToUpdate,
		Maintenance maintenanceToUpdate,
		MaintenanceResponsable maintenanceResponsable,
		String[] selectedWorkerIds,
		ObjectValidator objValidator,
		HttpServletRequest request, 
		HttpServletResponse response
	) throws ServletException, IOException {
		System.out.println("Sending machine to maintenance...");
		
		MaintenanceWorkerDAO maintenanceWorkerDAO = new MaintenanceWorkerDAO();
		Set<MaintenanceWorker> maintenanceWorkers = 
			Arrays.stream(selectedWorkerIds)
			    .map(id -> MaintenanceWorker.find(maintenanceWorkerDAO, Integer.parseInt(id)))
			    .filter(maintenanceWorker -> objValidator.hasValue(maintenanceWorker) && maintenanceWorker.isFree())
			    .collect(Collectors.toSet());
		System.out.println("Maintenance workers: " + maintenanceWorkers);
		// Vérification de la disponibilité des ouvriers
		boolean areWorkersAvailable = maintenanceWorkers.size() == selectedWorkerIds.length;
		if (!areWorkersAvailable) {
			System.out.println("One or more selected maintenance workers are not available.");
			forwardWithError(request, response, "One or more selected maintenance workers are not available.");
			return;
		}
		
		// Création de l'objet Maintenance
		System.out.println("Creating maintenance...");
		Maintenance maintenanceToInsert = new Maintenance(
			Optional.empty(), 
			LocalDateTime.now(), 
			Optional.empty(), 
			Optional.empty(),
			Optional.empty(), 
			MaintenanceStatus.IN_PROGRESS, 
			machineToUpdate, 
			maintenanceWorkers,
			maintenanceResponsable,
			new NumericValidator(),
			new StringValidator(), 
			new ObjectValidator(), 
			new DateValidator()
		);
		System.out.println("Maintenance created: " + maintenanceToInsert);
		machineToUpdate.setStatus(MachineStatus.IN_MAINTENANCE);
		
		// Mise à jour en base de données
		if (	
			!updateMachineInDatabase(machineToUpdate, request, response) || 
			!insertMaintenanceInDatabase(maintenanceToInsert, request, response)
		) {
			System.out.println("Failed to send machine to maintenance.");
			forwardWithError(request, response, "Failed to send machine to maintenance.");
			return;
		}
	}
	
	private void processValidationAction(
		Machine machineToUpdate, 
		Maintenance maintenanceToUpdate, 
		String actionParam,
		HttpServletRequest request,
		HttpServletResponse response
	) throws ServletException, IOException 
	{
		if (VALIDATE_ACTION.equals(actionParam)) {
			System.out.println("VALIDATE_ACTION");
	    	machineToUpdate.setStatus(MachineStatus.OK);
	    	maintenanceToUpdate.setStatus(MaintenanceStatus.DONE);
	    	
		} else if (RESTART_MAINTENANCE_ACTION.equals(actionParam)) {
			System.out.println("RESTART_MAINTENANCE_ACTION");
			maintenanceToUpdate.setStatus(MaintenanceStatus.IN_PROGRESS);
			maintenanceToUpdate.setEndDateTime(Optional.of(LocalDateTime.now()));
			machineToUpdate.setStatus(MachineStatus.IN_MAINTENANCE);
		}

	    // Mise à jour en base de données
	    if (
    		!updateMachineInDatabase(machineToUpdate, request, response) || 
    		!updateMaintenanceInDatabase(maintenanceToUpdate, request, response)
    	) 
    	{
	    	System.out.println("Failed to update machine and maintenance status.");
	    	return;
    	}
	}

	private String validateParameters(
	    String machineIdParam, 
	    String action, 
	    String[] workerIds, 
	    ObjectValidator objValidator
	) {
		System.out.println("Action: " + action);
	    if (!objValidator.hasValue(action)) {
	        return "Action is required.";
	    }

	    if (!isValidAction(action)) {
	        return "Invalid action.";
	    }

	    if (SEND_TO_MAINTENANCE_ACTION.equals(action) && !objValidator.hasValue(workerIds)) {    
            return "At least one maintenance worker is required.";
	    } else if (!objValidator.hasValue(machineIdParam)){
	    	return "Machine ID is required.";
	    }

	    return null;
	}

		private boolean isValidAction(String action) {
		    return VALIDATE_ACTION.equals(action) || 
		           RESTART_MAINTENANCE_ACTION.equals(action) || 
		           SEND_TO_MAINTENANCE_ACTION.equals(action);
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
	
	private boolean updateMachineInDatabase(
		Machine machine,
		HttpServletRequest request,
		HttpServletResponse response
	) throws ServletException, IOException 
	{
		boolean isMachineUpdated = machine.updateInDatabase(machineDAO); 
		if (!isMachineUpdated) {
			forwardWithError(request, response, "Failed to update machine status.");
			System.out.println("Failed to update machine status.");
			return false;
		}

		System.out.println("Machine " + machine + " updated successfully.");
		return true;
	}

	private boolean updateMaintenanceInDatabase(
		Maintenance maintenance,
		HttpServletRequest request,
		HttpServletResponse response
	) throws ServletException, IOException 
	{
		System.out.println("Updating maintenance...");
		boolean isMaintenanceUpdated = maintenance.updateInDatabase(new MaintenanceDAO());
		if (!isMaintenanceUpdated) {
			forwardWithError(request, response, "Failed to update maintenance status.");
			System.out.println("Failed to update maintenance status.");
			return false;
		}
		
		System.out.println("Maintenance " + maintenance.getId().orElse(-1) + " updated successfully.");
		return true;
	}
	
	private boolean insertMaintenanceInDatabase(
        Maintenance maintenance,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws ServletException, IOException 
    {
        boolean isMaintenanceInserted = maintenance.insertInDatabase(new MaintenanceDAO());
        if (!isMaintenanceInserted) {
            forwardWithError(request, response, "Failed to insert maintenance.");
            System.out.println("Failed to insert maintenance.");
            return false;
        }
        
        System.out.println("Maintenance " + maintenance + " inserted successfully.");
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
	    System.out.println("après do get");
	}
}
