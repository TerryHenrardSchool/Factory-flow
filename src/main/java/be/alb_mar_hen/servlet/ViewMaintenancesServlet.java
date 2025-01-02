package be.alb_mar_hen.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.alb_mar_hen.daos.FactoryFlowConnection;
import be.alb_mar_hen.daos.MaintenanceDAO;
import be.alb_mar_hen.models.Employee;
import be.alb_mar_hen.models.Maintenance;

public class ViewMaintenancesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private MaintenanceDAO maintenanceDAO;
	
    public ViewMaintenancesServlet() {
        super();
        maintenanceDAO = new MaintenanceDAO(FactoryFlowConnection.getInstance());
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		if (session.getAttribute("employee") == null) {
			request.setAttribute("errorMessage", "Access denied. Please log in to access this page.");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return;
		}
		
		Employee employee = (Employee) session.getAttribute("employee");
		
		System.out.println("Employee: " + employee.toString());
		
		
		List<Maintenance> maintenances = Maintenance.getMaintenances(maintenanceDAO, employee.getId().get());
		
		request.setAttribute("maintenances", maintenances);
		
		request.getRequestDispatcher("/WEB-INF/jsps/ViewMaintenances.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}