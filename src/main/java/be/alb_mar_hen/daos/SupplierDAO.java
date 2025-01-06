package be.alb_mar_hen.daos;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import be.alb_mar_hen.models.Supplier;

public class SupplierDAO extends DAO<Supplier>{

	public SupplierDAO(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(Supplier obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Supplier obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Supplier find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Supplier> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Supplier> findAll(Map<String, Object> criteria) {
		// TODO Auto-generated method stub
		return null;
	}

}
