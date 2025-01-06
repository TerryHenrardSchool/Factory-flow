package be.alb_mar_hen.daos;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import be.alb_mar_hen.models.Zone;

public class ZoneDAO extends DAO<Zone>{

	public ZoneDAO(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(Zone obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Zone obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Zone find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Zone> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Zone> findAll(Map<String, Object> criteria) {
		// TODO Auto-generated method stub
		return null;
	}
}
