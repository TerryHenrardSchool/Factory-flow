package be.alb_mar_hen.models;

import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import be.alb_mar_hen.validators.NumericValidator;
import be.alb_mar_hen.validators.ObjectValidator;
import be.alb_mar_hen.validators.StringValidator;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "keySupplier")
public class Supplier {
	
	// Validators
	private NumericValidator numericValidator;
	private StringValidator stringValidator;
	private ObjectValidator objectValidator;
	
	// Attributes
	private Optional<Integer> id;
	private String name;
	
	// Relations
	private MachineType machineType;
	
	// Constructors
	public Supplier(
		Optional<Integer> id, 
		String name,
		MachineType machineType,
		NumericValidator numericValidator, 
		StringValidator stringValidator, 
		ObjectValidator objectValidator
	) {
		this.numericValidator = numericValidator;
		this.stringValidator = stringValidator;
		this.objectValidator = objectValidator;
		setId(id);
		setName(name);
		setMachineType(machineType);
	}
	
	public Supplier() {
        this.numericValidator = new NumericValidator();
        this.stringValidator = new StringValidator();
        this.objectValidator = new ObjectValidator();
	}

	// Getters
	public Optional<Integer> getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public MachineType getMachineType() {
		return machineType;
	}

	// Setters
	public void setId(Optional<Integer> id) {
		if (!objectValidator.hasValue(id)) {
			throw new NullPointerException("Id must have a value.");
		}
		
	    if (!numericValidator.isPositiveOrEqualToZero(id)) {
	        throw new IllegalArgumentException("Id must be greater than or equal to 0");
	    }
	    
	    this.id = id;
	}

	public void setName(String name) {
		if (!stringValidator.hasValue(name)) {
			throw new NullPointerException("Name must have a value.");
		}
		
		this.name = name;
	}
	
	public void setMachineType(MachineType machineType) {
		if (!objectValidator.hasValue(machineType)) {
			throw new NullPointerException("Machine type must have a value.");
		}

		this.machineType = machineType;
	}

	// Override methods
	@Override
	public String toString() {
		return "Supplier [id=" + id.orElse(null) + 
			", name=" + name + 
			", machineType=" + machineType + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id.orElse(0), machineType, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (
			!objectValidator.hasValue(obj) || 
			getClass() != obj.getClass()
		) {
			return false;
		}
		
		Supplier other = (Supplier) obj;
		return Objects.equals(id.orElse(0), other.id.orElse(0)) &&
			Objects.equals(machineType, other.machineType) && 
			Objects.equals(name, other.name);
	}
}
