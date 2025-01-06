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

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "keyMaintenanceResponsable")
@JsonIgnoreProperties(ignoreUnknown = true)
public class MaintenanceResponsable extends Employee{
	
	// Validators
	private ObjectValidator objectValidator;
	
	// Attributes
	private Set<Maintenance> maintenances = new HashSet<Maintenance>();
		
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
	}
	
	public MaintenanceResponsable() {
		super();
		this.objectValidator = new ObjectValidator();
	}
	
	// Getters
	public Set<Maintenance> getMaintenances() {
		return maintenances;
	}
	
	// Setters
	public void setMaintenances(Set<Maintenance> maintenances) {
		if (!objectValidator.hasValue(maintenances)) {
			throw new IllegalArgumentException("MaintenanceResponsable setMaintenances error");
		}
		
		if (maintenances.isEmpty()) {
			throw new IllegalArgumentException("MaintenanceResponsable setMaintenances error");
		}
		
		this.maintenances = maintenances;
	}
	
	// Methods
	public boolean addMaintenance(Maintenance maintenance) {
		if (!objectValidator.hasValue(maintenance)) {
			throw new IllegalArgumentException("MaintenanceResponsable addMaintenance error");
		}

		return maintenances.add(maintenance);
	}
	
	// Override methods
	@Override
	public String toString() {
		return super.toString() + "MaintenanceResponsable";
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
















