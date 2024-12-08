package be.alb_mar_hen.models;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import be.alb_mar_hen.formatters.StringFormatter;
import be.alb_mar_hen.validators.NumericValidator;
import be.alb_mar_hen.validators.ObjectValidator;
import be.alb_mar_hen.validators.StringValidator;

public class MaintenanceResponsable extends Employee{
	
	// Validators
	private ObjectValidator objectValidator;
	
	// Relations
	private Set<Maintenance> maintenances;
	
	// Constructors
	public MaintenanceResponsable(
		Optional<Integer> id, 
		String matricule, 
		String password, 
		String firstName, 
		String lastName,
		ObjectValidator	objectValidator,
		StringValidator stringValidator, 
		NumericValidator numericValidator,
		StringFormatter stringFormatter
	) {
		super(id, matricule, password, firstName, lastName, stringValidator, numericValidator, objectValidator, stringFormatter);
		this.objectValidator = objectValidator;
		maintenances = new HashSet<>();
	}
	
	// Getters
	public Set<Maintenance> getMaintenances() {
		return maintenances;
	}
	
	// Methods
	public boolean addMaintenance(Maintenance maintenance) {
	    if (!objectValidator.hasValue(maintenance)) {
	        throw new IllegalArgumentException("Maintenance must have a value.");
	    }

	    boolean added = maintenances.add(maintenance);
	    if (added) {
	        maintenance.setMaintenanceResponsable(this);
	    }

	    return added;
	}
	
	// Override methods
	@Override
	public String toString() {
		return super.toString() + "MaintenanceResponsable [maintenances=" + maintenances + "]";
	}
	
	@Override
    public boolean equals(Object object) {
    	if(!(object instanceof Employee)) {
    		return false;
    	}
    	
    	return super.equals((Employee) object) 
			&& maintenances.equals(((MaintenanceResponsable) object).getMaintenances());
    }
	
	@Override
	public int hashCode() {
		return super.hashCode() + maintenances.hashCode();
	}
}
















