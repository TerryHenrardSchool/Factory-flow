package be.alb_mar_hen.daos;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class SupplierDAO extends DAO<SupplierDAO>{

	public SupplierDAO(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(SupplierDAO obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(SupplierDAO obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public SupplierDAO find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SupplierDAO> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SupplierDAO> findAll(Map<String, Object> criteria) {
		// TODO Auto-generated method stub
		return null;
	}

}
