package be.alb_mar_hen.servlet;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.json.JSONException;

import be.alb_mar_hen.models.Employee;
import be.alb_mar_hen.validators.StringValidator;
import be.alb_mar_hen.validators.NumericValidator;
import be.alb_mar_hen.validators.ObjectValidator;
import be.alb_mar_hen.formatters.StringFormatter;

@WebServlet("/AuthenticationServlet")
public class AuthenticationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private StringValidator stringValidator;
    private NumericValidator numericValidator;
    private ObjectValidator objectValidator;
    private StringFormatter stringFormatter;

    public AuthenticationServlet() {
        super();
        // Initialize validators and formatter
        this.stringValidator = new StringValidator();
        this.numericValidator = new NumericValidator();
        this.objectValidator = new ObjectValidator();
        this.stringFormatter = new StringFormatter();
    }

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

        // Parse the JSON response
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse); 
            String role = jsonObject.getString("role");
            String firstName = jsonObject.getString("firstName");
            String lastName = jsonObject.getString("lastName");
            String matriculeFromApi = jsonObject.getString("matricule");
            Optional<Integer> id = jsonObject.has("id") ? Optional.of(jsonObject.optInt("id")) : Optional.empty();

            // Create a concrete Employee object
            Employee concreteEmployee = Employee.createEmployeeFromJson(
                role,
                id,
                matriculeFromApi,
                password,
                firstName,
                lastName,
                stringValidator,
                numericValidator,
                objectValidator,
                stringFormatter
            );

            // Save the object in the session
            request.getSession().setAttribute("employee", concreteEmployee);
            
            // Redirect to the appropriate page based on role
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
