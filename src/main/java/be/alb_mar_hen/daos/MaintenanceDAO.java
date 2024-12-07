package be.alb_mar_hen.daos;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import be.alb_mar_hen.models.Maintenance;

public class MaintenanceDAO extends DAO<Maintenance>{

	public MaintenanceDAO(Connection conn) {
		super(conn);
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Maintenance> findAll(Map<String, Object> criteria) {
		// TODO Auto-generated method stub
		return null;
	}
}
