package be.alb_mar_hen.models;

import java.util.Objects;

import be.alb_mar_hen.validators.NumericValidator;
import be.alb_mar_hen.validators.StringValidator;

public class MachineType {
	private NumericValidator numericValidator;
	private StringValidator stringValidator;
	
	private int id;
	private String typeName;
	private double price;
	private int daysBeforeMaintenance;

	public MachineType(
		int id, 
		String type, 
		double price, 
		int daysBeforeMaintenance, 
		NumericValidator numericValidator, 
		StringValidator stringValidator
	) {
		this.numericValidator = numericValidator;
		this.stringValidator = stringValidator;
		setId(id);
		setTypeName(type);
		setPrice(price);
		setDaysBeforeMaintenance(daysBeforeMaintenance);
	}

	public MachineType(
		String type, 
		double price, 
		int daysBeforeMaintenance, 
		NumericValidator numericValidator, 
		StringValidator stringValidator
	) {
		this(0, type, price, daysBeforeMaintenance, numericValidator, stringValidator);
	}

	public int getId() {
		return id;
	}

	public String getType() {
		return typeName;
	}
	
	public double getPrice() {
		return price;
	}
	
	public int getDaysBeforeMaintenance() {
		return daysBeforeMaintenance;
	}
	
	void setId(int id) {
		if (!numericValidator.isPositiveOrEqualToZero(id)) {
			throw new IllegalArgumentException("The id must be positive or equal to zero");
		}
		
		this.id = id;
	}
	
	void setTypeName(String typeName) {
		if (!stringValidator.hasValue(typeName)) {
			throw new IllegalArgumentException("The type name must have a value.");
		}
		
		this.typeName = typeName;
	}
	
	void setPrice(double price) {
		if (!numericValidator.isPositive(price)) {
			throw new IllegalArgumentException("The price must be positive.");
		}

		this.price = price;
	}
	
	void setDaysBeforeMaintenance(int daysBeforeMaintenance) {
		if (!numericValidator.isPositive(daysBeforeMaintenance)) {
			throw new IllegalArgumentException("The days before maintenance must be positive.");
		}

		this.daysBeforeMaintenance = daysBeforeMaintenance;
	}

	@Override
	public String toString() {
		return "MachineType [numericValidator=" + numericValidator + ", stringValidator=" + stringValidator + ", id="
				+ id + ", typeName=" + typeName + ", price=" + price + ", daysBeforeMaintenance="
				+ daysBeforeMaintenance + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(daysBeforeMaintenance, id, numericValidator, price, stringValidator, typeName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MachineType other = (MachineType) obj;
		return daysBeforeMaintenance == other.daysBeforeMaintenance && id == other.id
				&& Objects.equals(numericValidator, other.numericValidator)
				&& Double.doubleToLongBits(price) == Double.doubleToLongBits(other.price)
				&& Objects.equals(stringValidator, other.stringValidator) && Objects.equals(typeName, other.typeName);
	}
}
