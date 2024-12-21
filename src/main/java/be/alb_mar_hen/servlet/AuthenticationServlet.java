package be.alb_mar_hen.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.json.JSONException;

import be.alb_mar_hen.models.Employee;

/**
 * Servlet implementation class AuthenticationServlet
 */
@WebServlet("/AuthenticationServlet")
public class AuthenticationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthenticationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String matricule = request.getParameter("matricule");
        String password = request.getParameter("password");

        if (matricule == null || matricule.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Matricule is required.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        if (password == null || password.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Password is required.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        if (!password.matches(Employee.PASSWORD_REGEX)) {
            request.setAttribute("errorMessage", "Password must be at least 8 characters long, and include at least one letter and one number.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        String jsonResponse = Employee.authenticateEmployee(matricule, password);

        if (jsonResponse == null || jsonResponse.isEmpty()) {
            request.setAttribute("errorMessage", "Invalid matricule or password.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        // Analyser le JSON pour obtenir les informations de l'employé
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse); 
            String role = jsonObject.getString("role");
            String firstName = jsonObject.getString("firstName");
            String lastName = jsonObject.getString("lastName");
            String matriculeFromApi = jsonObject.getString("matricule");

            // Créer l'objet Employee concret en fonction du rôle
            Employee concreteEmployee = employeeService.createEmployeeFromJson(role, firstName, lastName, matriculeFromApi);

            // Enregistrer l'objet dans la session
            request.getSession().setAttribute("employee", concreteEmployee);
            
            // Rediriger vers la page appropriée en fonction du rôle
            if ("Maintenance Responsable".equals(role)) {
                response.sendRedirect("maintenanceResponsableHome.jsp");
            } else if ("Maintenance Worker".equals(role)) {
                response.sendRedirect("maintenanceWorkerHome.jsp");
            } else if ("Purchasing Agent".equals(role)) {
                response.sendRedirect("purchasingAgentHome.jsp");
            } else {
                response.sendRedirect("defaultHome.jsp");
            }

        } catch (JSONException e) {
            request.setAttribute("errorMessage", "Error processing the response from the server.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

}
