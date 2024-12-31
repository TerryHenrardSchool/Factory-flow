package be.alb_mar_hen.models;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import be.alb_mar_hen.formatters.StringFormatter;
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
}