package be.alb_mar_hen.daos;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import be.alb_mar_hen.models.MaintenanceResponsable;

public class MaintenanceResponsableDAO extends DAO<MaintenanceResponsable>{

	public MaintenanceResponsableDAO(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(MaintenanceResponsable obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(MaintenanceResponsable obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MaintenanceResponsable find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MaintenanceResponsable> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MaintenanceResponsable> findAll(Map<String, Object> criteria) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
