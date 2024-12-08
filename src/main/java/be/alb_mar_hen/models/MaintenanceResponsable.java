package be.alb_mar_hen.models;

import be.alb_mar_hen.formatters.StringFormatter;
import be.alb_mar_hen.validators.NumericValidator;
import be.alb_mar_hen.validators.ObjectValidator;
import be.alb_mar_hen.validators.StringValidator;

public class MaintenanceResponsable extends Employee{

	public MaintenanceResponsable(
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

	public MaintenanceResponsable(
			String matricule, 
			String password, 
			String firstName,
			String lastName,
			ObjectValidator objectValidator, 
			StringValidator stringValidator, 
			NumericValidator numericValidator,
			StringFormatter stringFormatter
	) {
		super(matricule, password, firstName, lastName, stringValidator, numericValidator, stringFormatter);
	}
	
}
