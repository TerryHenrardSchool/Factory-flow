package be.alb_mar_hen.daos;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

import be.alb_mar_hen.models.MaintenanceWorker;
import be.alb_mar_hen.validators.ObjectValidator;

public class MaintenanceWorkerDAO extends DAO<MaintenanceWorker>{

	public MaintenanceWorkerDAO(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(MaintenanceWorker obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(MaintenanceWorker obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MaintenanceWorker find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MaintenanceWorker> findAll() {
		List<MaintenanceWorker> maintenanceWorkers = new ArrayList<>();
		
		try {
			String responseBody = getResource()
				.path("/maintenanceWorker")
				.accept(MediaType.APPLICATION_JSON)
				.get(String.class);
			
			ObjectValidator objectValidator = new ObjectValidator();
			if (!objectValidator.hasValue(responseBody)) {
				return maintenanceWorkers;
			}
			
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.registerModule(new Jdk8Module());
			
			maintenanceWorkers = objectMapper.readValue(
				responseBody,
				objectMapper.getTypeFactory().constructCollectionType(List.class, MaintenanceWorker.class)
			);
		} catch (Exception e) {
            e.printStackTrace();
            return maintenanceWorkers;
        }
		
        return maintenanceWorkers;

	}

	@Override
	public List<MaintenanceWorker> findAll(Map<String, Object> criteria) {
		// TODO Auto-generated method stub
		return null;
	}
}
