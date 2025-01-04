package be.alb_mar_hen.models;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import be.alb_mar_hen.daos.PurchasingAgentDAO;
import be.alb_mar_hen.formatters.StringFormatter;
import be.alb_mar_hen.serializers.CustomLocalDateTimeSerializer;
import be.alb_mar_hen.validators.NumericValidator;
import be.alb_mar_hen.validators.ObjectValidator;
import be.alb_mar_hen.validators.StringValidator;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "keyPurchasingAgent")
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
		
		SimpleModule module = new SimpleModule();
		module.addSerializer(new CustomLocalDateTimeSerializer());
	    
	    objectMapper.registerModule(new Jdk8Module());
	    objectMapper.registerModule(new JavaTimeModule());
        
        System.out.println(machineJson);
        boolean success = false;
        try {
			Machine machine = objectMapper.readValue(machineJson, Machine.class);
			PurchasingAgentDAO purchasingAgentDAO = new PurchasingAgentDAO();
			System.out.println(this.getId().get());
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