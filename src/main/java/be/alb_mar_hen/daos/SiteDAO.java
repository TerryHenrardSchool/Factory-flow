package be.alb_mar_hen.daos;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import be.alb_mar_hen.models.Site;

public class SiteDAO extends DAO<Site>{

	public SiteDAO(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(Site obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Site obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Site find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Site> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Site> findAll(Map<String, Object> criteria) {
		// TODO Auto-generated method stub
		return null;
	}
}
