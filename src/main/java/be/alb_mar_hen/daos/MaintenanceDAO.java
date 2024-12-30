package be.alb_mar_hen.daos;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.*;
import com.sun.jersey.api.client.config.*;
import be.alb_mar_hen.models.Maintenance;

public class MaintenanceDAO extends DAO<Maintenance>{

	public MaintenanceDAO(Connection conn) {
		super(conn);
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
		// TODO Auto-generated method stub
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
			
			System.out.println("Response: " + response);
			
			ObjectMapper mapper = new ObjectMapper();
			
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
}
