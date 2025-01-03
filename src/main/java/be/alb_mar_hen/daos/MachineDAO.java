package be.alb_mar_hen.daos;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import be.alb_mar_hen.models.Machine;
import be.alb_mar_hen.serializers.CustomLocalDateTimeSerializer;
import be.alb_mar_hen.utils.ObjectCreator;
import be.alb_mar_hen.validators.ObjectValidator;

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
	    String response = sendGetRequest("/machine/getAll");
	    
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
	
	public Collection<Machine> findAll_terry() {
	    Collection<Machine> machines = new ArrayList<>();

	    try {
	        String responseBody = getResource()
					        		.path("/machine")
					        		.accept(MediaType.APPLICATION_JSON)
					        		.get(String.class);
	        
	        if (responseBody == null || responseBody.isEmpty()) {
	            System.out.println("No machines");
	            return machines;
	        }

	        ObjectMapper mapper = new ObjectMapper();
		    mapper.registerModule(new Jdk8Module());
		    
	        machines = mapper.readValue(
	            responseBody,
	            mapper.getTypeFactory().constructCollectionType(List.class, Machine.class)
	        );
	    } catch (Exception e) {
	        e.printStackTrace();
	        return machines;
	    }

	    return machines;
	}


	@Override
	public List<Machine> findAll(Map<String, Object> criteria) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
