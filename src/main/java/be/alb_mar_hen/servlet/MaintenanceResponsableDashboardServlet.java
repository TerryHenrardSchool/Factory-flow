package be.alb_mar_hen.servlet;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;
		}
				
		Collection<Machine> machines = machineDAO.findAll_terry();
		
		if (machines.isEmpty()) {
			request.setAttribute("errorMessage", "No machines found.");
		}
				
		request.setAttribute("machines", machines);
		request.getRequestDispatcher("/WEB-INF/jsps/MaintenanceResponsableDashboard.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		String machineIdParam  = request.getParameter("machineId");
		String action = request.getParameter("action");
		
		ObjectValidator objValidator = new ObjectValidator();
		
		// Validation de l'action
		if (!objValidator.hasValue(action)) {
			request.setAttribute("errorMessage", "Action is required.");
			doGet(request, response);
			return;
		}
		
		// Validation de l'action 
		if (!action.equals(VALIDATE_ACTION) && !action.equals(RESTART_MAINTENANCE_ACTION)) {
			request.setAttribute("errorMessage", "Invalid action.");
            doGet(request, response);
            return;
		}
		
        // Validation de l'ID de la machine
		if (!objValidator.hasValue(machineIdParam)) {
			request.setAttribute("errorMessage", "Machine ID is required.");
			doGet(request, response);
			return;
		}
		
		// Conversion de l'ID de la machine en entier
		int machineId;
		try {
            machineId = Integer.parseInt(machineIdParam);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Machine ID must be a number.");
            doGet(request, response);
            return;
        }
		
		NumericValidator numValidator = new NumericValidator();
		if (!numValidator.isPositive(machineId)) {
            request.setAttribute("errorMessage", "Machine ID must be a positive number.");
            doGet(request, response);
            return;
        }
		
		// Réupérer la machine concernée
		Machine machineToUpdate = machineDAO.find(machineId);
		if (!objValidator.hasValue(machineToUpdate)) {
			request.setAttribute("errorMessage", "Machine not found.");
			doGet(request, response);
			return;
		}
		
		if (!machineToUpdate.getStatus().equals(MachineStatus.NEED_VALIDATION)) {
			request.setAttribute("errorMessage", "Machine already validated.");
			doGet(request, response);
			return;
		}
		
		Maintenance lastMaintenance = machineToUpdate.getLastMaintenance();
		if (action.equals(VALIDATE_ACTION)) {
			// Valider la maintenance de la machine
					
			machineToUpdate.setStatus(MachineStatus.OK);
			lastMaintenance.setStatus(MaintenanceStatus.DONE);
			
		} else if (action.equals(RESTART_MAINTENANCE_ACTION)) {
			// Recommencer la maintenance de la machine 

			machineToUpdate.setStatus(MachineStatus.OK);
			lastMaintenance.setStatus(MaintenanceStatus.IN_PROGRESS);
		}
		
		// Mise à jour du statut de la machine
		boolean isUpdated = machineDAO.update(machineToUpdate);
		if (!isUpdated) {
			request.setAttribute("errorMessage", "Failed to update machine status.");
			doGet(request, response);
			return;
		}
		
		// Mise à jour du statut de la maintenance
		MaintenanceDAO maintenanceDAO = new MaintenanceDAO();
		boolean isMaintenanceUpdated = maintenanceDAO.update(lastMaintenance);
		if (!isMaintenanceUpdated) {
			request.setAttribute("errorMessage", "Failed to update maintenance status.");
			doGet(request, response);
			return;
		}
		
        request.setAttribute("successMessage", "Machine " + machineToUpdate.getId().get() + " status updated successfully.");
		
		doGet(request, response);
	}
}
