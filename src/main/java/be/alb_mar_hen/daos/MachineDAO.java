package be.alb_mar_hen.daos;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import be.alb_mar_hen.models.Machine;
import be.alb_mar_hen.utils.ObjectCreator;

public class MachineDAO extends DAO<Machine>{

	public MachineDAO() {
		super();
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
        String response = sendGetRequest("/machines/getAll");
        
        if (response.startsWith("Error:")) {
            System.out.println("Error fetching data: " + response);
            return new ArrayList<>();
        }
        
        JSONArray jsonArray = new JSONArray(response);
        
        List<Machine> machines = new ArrayList<>();
        
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonMachine = jsonArray.getJSONObject(i);
            Object objectCreator = new ObjectCreator();
			Machine machine = ((ObjectCreator) objectCreator).createMachine(jsonMachine);
            machines.add(machine);
        }
        
        return machines;
    }

	@Override
	public List<Machine> findAll(Map<String, Object> criteria) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
