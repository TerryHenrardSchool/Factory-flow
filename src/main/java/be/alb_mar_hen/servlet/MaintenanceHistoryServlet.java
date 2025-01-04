package be.alb_mar_hen.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.alb_mar_hen.daos.MaintenanceDAO;
import be.alb_mar_hen.models.Maintenance;

public class MaintenanceHistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final MaintenanceDAO maintenanceDAO = new MaintenanceDAO();
	
    public MaintenanceHistoryServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("employee") == null) {
			response.sendRedirect("AuthenticationServlet");
			return;
		}
		
		List<Maintenance> maintenances = maintenanceDAO.findAll();
		
		request.setAttribute("maintenances", maintenances);
		
		request.getRequestDispatcher("/WEB-INF/jsps/MaintenanceHistory.jsp").forward(request, response);
	}
}