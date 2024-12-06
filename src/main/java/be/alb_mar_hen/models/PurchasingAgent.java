package be.alb_mar_hen.models;

import be.alb_mar_hen.validators.NumericValidator;
import be.alb_mar_hen.validators.ObjectValidator;
import be.alb_mar_hen.validators.StringValidator;

public class PurchasingAgent extends Person{

	public PurchasingAgent(
		int id, 
		String matricule, 
		String password, 
		String firstName, 
		String lastName,
		ObjectValidator ObjectValidator, 
		StringValidator stringValidator, 
		NumericValidator numericValidator
	) {                  
		super(id, matricule, password, firstName, lastName, ObjectValidator, stringValidator, numericValidator);
	}

	public PurchasingAgent(
		String matricule, 
		String password, 
		String firstName, 
		String lastName, 
		ObjectValidator objectValidator, 
		StringValidator stringValidator, 
		NumericValidator numericValidator
	) {
		super(matricule, password, firstName, lastName, objectValidator, stringValidator, numericValidator);
	}
}