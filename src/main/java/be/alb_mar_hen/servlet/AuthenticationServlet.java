package be.alb_mar_hen.servlet;

import java.io.IOException;
import java.util.AbstractMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.alb_mar_hen.models.Employee;

@WebServlet("/AuthenticationServlet")
public class AuthenticationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;


    public AuthenticationServlet() {
        super();   
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String matricule = request.getParameter("matricule");
        String password = request.getParameter("password");

        try {

            if (matricule == null || matricule.trim().isEmpty()) {
                throw new IllegalArgumentException("Matricule is required.");
            }

            if (password == null || password.trim().isEmpty()) {
                throw new IllegalArgumentException("Password is required.");
            }

            if (!password.matches(Employee.PASSWORD_REGEX)) {
                throw new IllegalArgumentException("Password must be at least 8 characters long, and include at least one letter and one number.");
            }
            
            AbstractMap.SimpleEntry<Employee, String> employeeAndRole = Employee.authenticateEmployee(matricule, password);
            
            if (employeeAndRole == null || employeeAndRole.getKey() == null) {
                throw new SecurityException("Invalid matricule or password.");
            }

            Employee concreteEmployee = employeeAndRole.getKey();
            String role = employeeAndRole.getValue();
            
            request.getSession().setAttribute("employee", concreteEmployee);
            
            switch (role) {
                case "Maintenance Responsable":
                    response.sendRedirect("MaintenanceResponsableDashboardServlet");
                    break;
                case "Maintenance Worker":
                    response.sendRedirect("ViewMaintenancesServlet");
                    break;
                case "Purchasing Agent":
                    response.sendRedirect("PurchasingAgentDashboardServlet");
                    break;
                default:
                    response.sendRedirect("AuthenticationServlet");
                    break;
            }

        } catch (IllegalArgumentException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsps/Authentication.jsp").forward(request, response);

        } catch (SecurityException e) {
            request.setAttribute("errorMessage", "Authentication failed: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsps/Authentication.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace(); // Logs
            request.setAttribute("errorMessage", "An unexpected error occurred. Please try again.");
            request.getRequestDispatcher("/WEB-INF/jsps/Authentication.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsps/Authentication.jsp").forward(request, response);
    }
}
