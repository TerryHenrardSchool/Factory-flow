package be.alb_mar_hen.models;

import java.util.Objects;

import be.alb_mar_hen.validators.NumericValidator;
import be.alb_mar_hen.validators.ObjectValidator;
import be.alb_mar_hen.validators.StringValidator;

public class Supplier {
	// Validators
	private NumericValidator numericValidator;
	private StringValidator stringValidator;
	private ObjectValidator objectValidator;
	
	// Attributes
	private int id;
	private String name;
	
	// Relations
	private MachineType machineType;
	
	// Constructors
	public Supplier(
		int id, 
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
	
	public Supplier(
        String name,
        MachineType machineType,
        NumericValidator numericValidator, 
        StringValidator stringValidator, 
        ObjectValidator objectValidator
    ) {
        this(0, name, machineType, numericValidator, stringValidator, objectValidator);
	}

	// Getters
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public MachineType getMachineType() {
		return machineType;
	}

	// Setters
	public void setId(int id) {
		if(!numericValidator.isPositiveOrEqualToZero(id)) {
			throw new IllegalArgumentException("Id must be greater or equal than 0.");
		}
		
		this.id = id;
	}

	public void setName(String name) {
		if (!stringValidator.isNullOrEmpty(name)) {
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
		return "Supplier [id=" + id + ", name=" + name + ", machineType=" + machineType + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, machineType, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (obj == null) {
			return false;
		}
		
		if (getClass() != obj.getClass()) {
			return false;
		}
		
		Supplier other = (Supplier) obj;
		return id == other.id && Objects.equals(machineType, other.machineType) && Objects.equals(name, other.name);
	}
}
