package be.alb_mar_hen.daos;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.internal.inject.Custom;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun.jersey.api.client.*;
import com.sun.jersey.api.client.config.*;
import be.alb_mar_hen.models.Maintenance;
import be.alb_mar_hen.serializers.*;

public class MaintenanceDAO extends DAO<Maintenance>{

	public MaintenanceDAO() {
		super();
	}
	
	@Override
	public WebResource getResource() {
        return resource;
	}

	@Override
	public boolean create(Maintenance obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Maintenance obj) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			
			SimpleModule module = new SimpleModule();
			module.addSerializer(new CustomLocalDateTimeSerializer());
			
			mapper.registerModule(module);
			mapper.registerModule(new Jdk8Module());
			
			String json = mapper.writeValueAsString(obj);
			
			ClientResponse response = 
					getResource()
					.path("maintenance")
					.type(MediaType.APPLICATION_JSON)
					.put(ClientResponse.class, json);
			
			if (response.getStatus() == Status.OK.getStatusCode()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public Maintenance find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Maintenance> findAll() {
		List<Maintenance> maintenancesList = new ArrayList<Maintenance>();
		
		try {			
			String response = getResource()
					.path("maintenance")
					.accept(MediaType.APPLICATION_JSON)
					.get(String.class);
			
			
			ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new Jdk8Module()); 
			mapper.registerModule(new JavaTimeModule());
			
			maintenancesList = mapper.readValue(response, mapper.getTypeFactory().constructCollectionType(List.class, Maintenance.class));
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return maintenancesList;
	}

	@Override
	public List<Maintenance> findAll(Map<String, Object> criteria) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Maintenance> findAll(int workerId){
		List<Maintenance> maintenancesList = new ArrayList<Maintenance>();
		
		try {			
			String response = getResource()
					.path("maintenance/" + workerId)
					.accept(MediaType.APPLICATION_JSON)
					.get(String.class);
			
			ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new Jdk8Module()); 
			
			maintenancesList = mapper.readValue(response, mapper.getTypeFactory().constructCollectionType(List.class, Maintenance.class));
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return maintenancesList;
	}
}
