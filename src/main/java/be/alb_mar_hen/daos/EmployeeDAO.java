package be.alb_mar_hen.daos;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

import be.alb_mar_hen.models.Employee;
import be.alb_mar_hen.models.MaintenanceResponsable;
import be.alb_mar_hen.models.MaintenanceWorker;
import be.alb_mar_hen.models.PurchasingAgent;

public class EmployeeDAO extends DAO<Employee> {
	
	
	public EmployeeDAO() {
		super();
	}

	@Override
	public boolean create(Employee obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Employee obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Employee find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Employee> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Employee> findAll(Map<String, Object> criteria) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Employee authenticateEmployee(String matricule, String password) throws SQLException {
	    String input = "{\"matricule\": \"" + matricule + "\", \"password\": \"" + password + "\"}";
	    String jsonResponse = null;

	    try {
	        jsonResponse = sendPostRequest("login/authenticate", input);

	        if (jsonResponse == null || jsonResponse.isEmpty()) {
	            throw new SQLException("Authentication failed: No response from authentication service.");
	        }

	        JSONObject jsonObject = new JSONObject(jsonResponse);
	        String role = jsonObject.getString("role");

	        ObjectMapper objectMapper = new ObjectMapper();
	        objectMapper.registerModule(new Jdk8Module()); 

	        Employee concreteEmployee = null;
	        switch (role) {
	            case "Maintenance Responsable":
	                concreteEmployee = objectMapper.readValue(jsonResponse, MaintenanceResponsable.class);
	                break;
	            case "Maintenance Worker":
	                concreteEmployee = objectMapper.readValue(jsonResponse, MaintenanceWorker.class);
	                break;
	            case "Purchasing Agent":
	                concreteEmployee = objectMapper.readValue(jsonResponse, PurchasingAgent.class);
	                break;
	            default:
	                throw new IllegalArgumentException("Unknown role: " + role);
	        }

	        return concreteEmployee;

	    } catch (SQLException e) {
	        throw new SQLException("Error while authenticating user: " + e.getMessage(), e);

	    } catch (JsonProcessingException e) {
	        throw new SQLException("Error while processing the JSON response: " + e.getMessage(), e);

	    } catch (Exception e) {
	        throw new SQLException("An unexpected error occurred during authentication: " + e.getMessage(), e);
	    }
	}

}
