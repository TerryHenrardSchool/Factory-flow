package be.alb_mar_hen.daos;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import be.alb_mar_hen.models.Machine;
import be.alb_mar_hen.utils.ObjectCreator;

public class MachineDAO extends DAO<Machine>{

	public MachineDAO() {
		super();
	}

	@Override
	public boolean create(Machine obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Machine obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Machine find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Machine> findAll() {
	    String response = sendGetRequest("/machines/getAll");
	    
	    if (response.startsWith("Error:")) {
	        System.out.println("Error fetching data: " + response);
	        return new ArrayList<>();
	    }

	    try {
	        ObjectMapper objectMapper = new ObjectMapper();
	        objectMapper.registerModule(new Jdk8Module());
	        
	        List<Machine> machines = objectMapper.readValue(response, new TypeReference<List<Machine>>() {});
	        return machines;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ArrayList<>();
	    }
	}

	@Override
	public List<Machine> findAll(Map<String, Object> criteria) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
