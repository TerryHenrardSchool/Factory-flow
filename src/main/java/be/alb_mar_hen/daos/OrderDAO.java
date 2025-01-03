package be.alb_mar_hen.daos;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import be.alb_mar_hen.models.Machine;
import be.alb_mar_hen.models.Order;

public class OrderDAO extends DAO<Order>{

	public OrderDAO() {
		super();
	}

	@Override
	public boolean create(Order obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Order obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Order find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> findAll() {
		List<Order> orders = new ArrayList<>();

	    try {
	        String responseBody = getResource()
					        		.path("/order")
					        		.accept(MediaType.APPLICATION_JSON)
					        		.get(String.class);
	        
	        if (responseBody == null || responseBody.isEmpty()) {
	            System.out.println("No machines");
	            return orders;
	        }

	        ObjectMapper mapper = new ObjectMapper();
	        mapper.registerModule(new JavaTimeModule()); 
	        mapper.registerModule(new Jdk8Module());
	        orders = mapper.readValue(
	            responseBody,
					mapper.getTypeFactory().constructCollectionType(List.class, Order.class)
	        );
	    } catch (Exception e) {
	        e.printStackTrace();
	        return orders;
	    }

	    return orders;
	}

	@Override
	public List<Order> findAll(Map<String, Object> criteria) {
		// TODO Auto-generated method stub
		return null;
	}
}
