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

@WebServlet("/login")
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

        try {
            // entry validation
            if (matricule == null || matricule.trim().isEmpty()) {
                throw new IllegalArgumentException("Matricule is required.");
            }

            if (password == null || password.trim().isEmpty()) {
                throw new IllegalArgumentException("Password is required.");
            }

            if (!password.matches(Employee.PASSWORD_REGEX)) {
                throw new IllegalArgumentException("Password must be at least 8 characters long, and include at least one letter and one number.");
            }

            // authenticate by calling the api
            String jsonResponse = Employee.authenticateEmployee(matricule, password);
            System.out.println("JSON Response: " + jsonResponse);

            if (jsonResponse == null || jsonResponse.isEmpty()) {
                throw new SecurityException("Invalid matricule or password.");
            }
            
            if (jsonResponse.startsWith("Error:")) {
                String errorCode = jsonResponse.split(":")[1].trim();
                if ("401".equals(errorCode)) {
                    request.setAttribute("errorMessage", "Invalid credentials. Please try again.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                } else {
                    request.setAttribute("errorMessage", "An unexpected error occurred: " + jsonResponse);
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                }
            }

            // json parsing
            JSONObject jsonObject = new JSONObject(jsonResponse);
            String role = jsonObject.getString("role");
            String firstName = jsonObject.getString("firstName");
            String lastName = jsonObject.getString("lastName");
            String matriculeFromApi = jsonObject.getString("matricule");
            Optional<Integer> id = jsonObject.has("employeeId") ? Optional.of(jsonObject.optInt("employeeId")) : Optional.empty();

            // create employee object (concrete object)
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

            request.getSession().setAttribute("employee", concreteEmployee);
            
            switch (role) {
                case "Maintenance Responsable":
                    response.sendRedirect("maintenanceResponsableHome.jsp");
                    break;
                case "Maintenance Worker":
                    response.sendRedirect("maintenanceWorkerHome.jsp");
                    break;
                case "Purchasing Agent":
                    response.sendRedirect("purchasingAgentHome.jsp");
                    break;
                default:
                    response.sendRedirect("defaultHome.jsp");
                    break;
            }

        } catch (IllegalArgumentException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("login.jsp").forward(request, response);

        } catch (SecurityException e) {
            request.setAttribute("errorMessage", "Authentication failed: " + e.getMessage());
            request.getRequestDispatcher("login.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace(); // logs
            request.setAttribute("errorMessage", "An unexpected error occurred. Please try again.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}
