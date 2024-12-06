package be.alb_mar_hen.models;

import java.util.Objects;

import be.alb_mar_hen.validators.NumericValidator;
import be.alb_mar_hen.validators.ObjectValidator;

public class Supplier {
	private NumericValidator numericValidator;
	private ObjectValidator objectValidator;
	
	private int id;
	private String name;
	
	public Supplier(
			NumericValidator numericValidator, 
			ObjectValidator objectValidator, 
			int id, 
			String name
	) {
		this.numericValidator = numericValidator;
		this.objectValidator = objectValidator;
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(int id) {
		if(!numericValidator.isPositiveOrEqualToZero(id)) {
			throw new IllegalArgumentException("Id must be greater or equal than 0.");
		}
		
		this.id = id;
	}

	public void setName(String name) {
		if (!objectValidator.hasValue(name)) {
			throw new NullPointerException("Name must have a value.");
		}
		
		this.name = name;
	}

	@Override
	public String toString() {
		return "Supplier [id=" + id + ", name=" + name + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Supplier other = (Supplier) obj;
		return id == other.id && Objects.equals(name, other.name);
	}
}
