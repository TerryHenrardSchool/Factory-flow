package be.alb_mar_hen.models;

import be.alb_mar_hen.formatters.StringFormatter;
import be.alb_mar_hen.validators.NumericValidator;
import be.alb_mar_hen.validators.StringValidator;

public class PurchasingAgent extends Employee{
	
	// Constructors
	public PurchasingAgent(
		int id, 
		String matricule, 
		String password, 
		String firstName, 
		String lastName,
		StringValidator stringValidator, 
		NumericValidator numericValidator,
		StringFormatter stringFormatter
	) {                  
		super(id, matricule, password, firstName, lastName, stringValidator, numericValidator, stringFormatter);
	}

	public PurchasingAgent(
		String matricule, 
		String password, 
		String firstName, 
		String lastName, 
		StringValidator stringValidator, 
		NumericValidator numericValidator,
		StringFormatter stringFormatter
	) {
		super(0, matricule, password, firstName, lastName, stringValidator, numericValidator, stringFormatter);
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