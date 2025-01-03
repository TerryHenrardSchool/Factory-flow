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
import be.alb_mar_hen.models.Employee;
import be.alb_mar_hen.models.Machine;
import be.alb_mar_hen.validators.ObjectValidator;

/**
 * Servlet implementation class MaintenanceResponsableServlet
 */
public class MaintenanceResponsableDashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
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
		ObjectValidator objValidator = new ObjectValidator();
		String machineIdParam  = request.getParameter("machineId");   
		
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
		
		// Réupérer la machine concernée
		Machine machineToUpdate = machineDAO.find(machineId);
		if (!objValidator.hasValue(machineToUpdate)) {
			request.setAttribute("errorMessage", "Machine not found.");
			doGet(request, response);
			return;
		}
		System.out.println("Machine found: " + machineToUpdate);
		
		// Mise à jour du statut de la machine
		boolean isUpdated = machineDAO.update(machineToUpdate);
		if (!isUpdated) {
			request.setAttribute("errorMessage", "Failed to update machine status.");
			doGet(request, response);
			return;
		}
		
        request.setAttribute("successMessage", "Machine " + machineToUpdate.getId().get() + " status updated successfully.");
		
		doGet(request, response);
	}
}
