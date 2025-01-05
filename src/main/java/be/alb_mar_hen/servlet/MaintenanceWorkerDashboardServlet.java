package be.alb_mar_hen.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.alb_mar_hen.daos.FactoryFlowConnection;
import be.alb_mar_hen.daos.MaintenanceDAO;
import be.alb_mar_hen.daos.MaintenanceWorkerDAO;
import be.alb_mar_hen.enumerations.MachineStatus;
import be.alb_mar_hen.enumerations.MaintenanceStatus;
import be.alb_mar_hen.models.Employee;
import be.alb_mar_hen.models.Maintenance;
import be.alb_mar_hen.models.MaintenanceWorker;
import be.alb_mar_hen.validators.ObjectValidator;

public class MaintenanceWorkerDashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private final MaintenanceDAO maintenanceDAO;
    private final MaintenanceWorkerDAO maintenanceWorkerDAO = new MaintenanceWorkerDAO(FactoryFlowConnection.getInstance());
    private final ObjectValidator objectValidator = new ObjectValidator();
    
    public MaintenanceWorkerDashboardServlet() {
        super();
        maintenanceDAO = new MaintenanceDAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		if (session.getAttribute("employee") == null) {
			request.setAttribute("errorMessage", "Access denied. Please log in to access this page.");
			request.getRequestDispatcher("/WEB-INF/jsps/Authentication.jsp").forward(request, response);
			return;
		}
	
		Employee employee = (Employee) session.getAttribute("employee");	
		
		MaintenanceWorker maintenanceWorker = MaintenanceWorker.find(maintenanceWorkerDAO, employee.getId().get().intValue());
		
		//TODO get maintenances from a specific worker
		Set<Maintenance> maintenances = maintenanceWorker.getMaintenances();
		
		request.setAttribute("maintenances", maintenances);
		
		request.getRequestDispatcher("/WEB-INF/jsps/MaintenanceWorkerDashboard.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!objectValidator.hasValue(request.getParameter("maintenanceId")) || !objectValidator.hasValue(request.getParameter("report"))) {
			doGet(request, response);
			return;
		}
		
		int idMaintenance = Integer.parseInt(request.getParameter("maintenanceId"));
		
		String reportMaintenance = request.getParameter("report");
		
		Maintenance maintenance = Maintenance.find(idMaintenance, maintenanceDAO);
		
		if (!maintenance.getStatus().equals(MaintenanceStatus.IN_PROGRESS)) {
			doGet(request, response);
			return;
		}
		
		maintenance.setReport(Optional.of(reportMaintenance));
		
		maintenance.setStatus(MaintenanceStatus.PENDING_VALIDATION);
		
		maintenance.updateInDatabase(maintenanceDAO);
		
		doGet(request, response);
	}
}