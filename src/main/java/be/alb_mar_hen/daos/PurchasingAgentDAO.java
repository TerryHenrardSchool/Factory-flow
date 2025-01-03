package be.alb_mar_hen.daos;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

import be.alb_mar_hen.models.Employee;
import be.alb_mar_hen.models.Machine;
import be.alb_mar_hen.models.MaintenanceResponsable;
import be.alb_mar_hen.models.MaintenanceWorker;
import be.alb_mar_hen.models.PurchasingAgent;

public class PurchasingAgentDAO extends DAO<PurchasingAgent>{

	public PurchasingAgentDAO() {
		super();
	}

	@Override
	public boolean create(PurchasingAgent obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(PurchasingAgent obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PurchasingAgent find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PurchasingAgent> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PurchasingAgent> findAll(Map<String, Object> criteria) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean buyMachine(Machine machine, int employeeid) throws SQLException {
	    ObjectMapper objectMapper = new ObjectMapper();
	    objectMapper.registerModule(new Jdk8Module());

	    boolean result = false;
	    try {
	        String machineJson = objectMapper.writeValueAsString(machine);

	        JSONObject jsonObject = new JSONObject(machineJson);
	        jsonObject.put("purchasingAgentId", employeeid);
	        
	        String jsonResponse = sendPostRequest("purchasingAgent/buyMachine", jsonObject.toString());
	        System.out.println(jsonResponse);
	        if (jsonResponse.contains("201")) {
	            result = true;
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}


}
