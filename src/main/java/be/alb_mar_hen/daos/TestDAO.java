package be.alb_mar_hen.daos;

import java.nio.channels.SelectableChannel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestDAO extends DAO{
	public TestDAO(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> findAll() {
		List<String> nameStrings = new ArrayList<String>();
		
		String queryString = "SELECT ename FROM emp";
		
		try {
			PreparedStatement statement = connection.prepareStatement(queryString);
			
			ResultSet res = statement.executeQuery();
			
			while (res.next()) {
				nameStrings.add(res.getString("ename"));
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		return nameStrings;
	}

	@Override
	public List findAll(Map criteria) {
		// TODO Auto-generated method stub
		return null;
	}
}
