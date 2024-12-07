package be.alb_mar_hen.daos;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import be.alb_mar_hen.models.PurchasingAgent;

public class PurchasingAgentDAO extends DAO<PurchasingAgent>{

	public PurchasingAgentDAO(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(PurchasingAgent obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(PurchasingAgent obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PurchasingAgent find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PurchasingAgent> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PurchasingAgent> findAll(Map<String, Object> criteria) {
		// TODO Auto-generated method stub
		return null;
	}

}
