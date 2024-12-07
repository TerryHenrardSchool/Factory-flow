package be.alb_mar_hen.daos;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import be.alb_mar_hen.models.Machine;

public class MachineDAO extends DAO<Machine>{

	public MachineDAO(Connection conn) {
		super(conn);
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Machine> findAll(Map<String, Object> criteria) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
