package be.alb_mar_hen.models;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

import be.alb_mar_hen.daos.PurchasingAgentDAO;
import be.alb_mar_hen.formatters.StringFormatter;
import be.alb_mar_hen.validators.NumericValidator;
import be.alb_mar_hen.validators.ObjectValidator;
import be.alb_mar_hen.validators.StringValidator;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PurchasingAgent extends Employee {
	
	// Constructors
	public PurchasingAgent(
		Optional<Integer> id, 
		String matricule, 
		String password, 
		String firstName, 
		String lastName,
		StringValidator stringValidator, 
		NumericValidator numericValidator,
		ObjectValidator objectValidator,
		StringFormatter stringFormatter
	) {                  
		super(
			id,
			matricule, 
			password,
			firstName,
			lastName, 
			stringValidator,
			numericValidator,
			objectValidator, 
			stringFormatter
		);
	}
	
	public PurchasingAgent() {
		super();		
	}

	// Override methods
	@Override
	public String toString() {
		return super.toString();
	}
	
	@Override
    public boolean equals(Object object) {
    	if(!(object instanceof Employee)) {
    		return false;
    	}

    	return super.equals((Employee) object);
    }
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
	public boolean buyMachine(String machineJson) {
		ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Jdk8Module());
        boolean success = false;
        try {
			Machine machine = objectMapper.readValue(machineJson, Machine.class);
			PurchasingAgentDAO purchasingAgentDAO = new PurchasingAgentDAO();
			success = purchasingAgentDAO.buyMachine(machine, this.getId().get());
		} catch (IOException e) {
			e.printStackTrace();
		} 
        catch (SQLException e) {
			e.printStackTrace();
		}
        
        return success;
    }
}