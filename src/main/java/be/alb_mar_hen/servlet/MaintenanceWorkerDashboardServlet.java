package be.alb_mar_hen.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.alb_mar_hen.daos.FactoryFlowConnection;
import be.alb_mar_hen.daos.MaintenanceDAO;
import be.alb_mar_hen.models.Employee;
import be.alb_mar_hen.models.Maintenance;

public class MaintenanceWorkerDashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private MaintenanceDAO maintenanceDAO;
    
    public MaintenanceWorkerDashboardServlet() {
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
		
		List<Maintenance> maintenances = Maintenance.getMaintenances(maintenanceDAO, employee.getId().get());
		
		request.setAttribute("maintenances", maintenances);
		
		request.getRequestDispatcher("/WEB-INF/jsps/MaintenanceWorkerDashboard.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("maintenanceId") == null || request.getParameter("report") == null) {
			doGet(request, response);
			return;
		}
		
		int idMaintenance = Integer.parseInt(request.getParameter("maintenanceId"));
		
		String reportMaintenance = request.getParameter("report");

		List<Maintenance> maintenances = Maintenance.getMaintenances(maintenanceDAO);
		
		Maintenance maintenance = maintenances.stream()
			.filter(curr -> curr.getId().get() == idMaintenance)
			.findFirst().get();
		
		maintenance.setReport(Optional.of(reportMaintenance));
		
		maintenance.update(maintenanceDAO);
		
		doGet(request, response);
	}
}