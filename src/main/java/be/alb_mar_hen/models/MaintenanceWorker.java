package be.alb_mar_hen.models;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import be.alb_mar_hen.formatters.StringFormatter;
import be.alb_mar_hen.validators.NumericValidator;
import be.alb_mar_hen.validators.ObjectValidator;
import be.alb_mar_hen.validators.StringValidator;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "keyMaintenanceWorker")
@JsonIgnoreProperties(ignoreUnknown = true)
public class MaintenanceWorker extends Employee{
	
	// Validators
	private ObjectValidator objectValidator;
	
	// Constructors
	public MaintenanceWorker(
		Optional<Integer> id, 
		String matricule, 
		String password,
		String firstName,
		String lastName,
		StringValidator stringValidator, 
		NumericValidator numericValidator,
		StringFormatter stringFormatter,
		ObjectValidator objectValidator
	) {
		super(id, matricule, password, firstName, lastName, stringValidator, numericValidator, objectValidator, stringFormatter);
		this.objectValidator = objectValidator;
	}
	
	public MaintenanceWorker() {
		super();
		this.objectValidator = new ObjectValidator();
	}
		
	// Override methods
	@Override
	public String toString() {
		return super.toString() + "MaintenanceWorker";
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
}
