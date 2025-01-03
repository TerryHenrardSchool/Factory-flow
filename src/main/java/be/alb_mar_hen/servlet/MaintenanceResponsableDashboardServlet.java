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
	private Collection<Machine> machines;
       
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
				
		machines = machineDAO.findAll_terry();
		
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
		int machineId = Integer.parseInt(request.getParameter("machineId"));
        Machine machineToUpdate = machines.stream().filter(m -> m.getId().get() == machineId).findFirst().orElse(null);
        ObjectValidator objValidator = new ObjectValidator();
		if (!objValidator.hasValue(machineToUpdate)) {
			request.setAttribute("errorMessage", "Machine not found.");
			request.getRequestDispatcher("/WEB-INF/jsps/MaintenanceResponsableDashboard.jsp").forward(request,response);
			return;
		}
        
		// TODO: Update machine status through API
		
		doGet(request, response);
	}
}
