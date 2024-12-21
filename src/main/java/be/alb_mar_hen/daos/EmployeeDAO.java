package be.alb_mar_hen.daos;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import be.alb_mar_hen.models.Employee;

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
	
	public String authenticateEmployee(String matricule, String password) {
        String input = "{\"matricule\": \"" + matricule + "\", \"password\": \"" + password + "\"}";
        return sendPostRequest("login", input);
    }

}
