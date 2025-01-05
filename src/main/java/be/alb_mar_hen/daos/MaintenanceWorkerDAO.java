package be.alb_mar_hen.daos;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

import be.alb_mar_hen.models.MaintenanceWorker;

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
		MaintenanceWorker maintenanceWorker = null;
		
		try {
			String response = getResource()
					.path("/maintenanceWorker/" + id)
					.accept("application/json")
					.get(String.class);
			
			System.out.println("Response : " + response);
			
			ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new Jdk8Module());
			
			maintenanceWorker = mapper.readValue(response, MaintenanceWorker.class);
			
			return maintenanceWorker;
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<MaintenanceWorker> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MaintenanceWorker> findAll(Map<String, Object> criteria) {
		// TODO Auto-generated method stub
		return null;
	}
}
