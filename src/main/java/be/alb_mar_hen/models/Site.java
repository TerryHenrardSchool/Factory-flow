package be.alb_mar_hen.models;

import java.util.Objects;

import be.alb_mar_hen.validators.NumericValidator;
import be.alb_mar_hen.validators.ObjectValidator;
import be.alb_mar_hen.validators.StringValidator;

public class Site {
	StringValidator stringValidator;
	NumericValidator numericValidator;
	
	private int id;
	private String city;
	
	public Site(
		int id, 
		String city,
		StringValidator stringValidator, 
		NumericValidator numericValidator
	) {
		this.stringValidator = stringValidator;
		this.numericValidator = numericValidator;
		setId(id);
		setCity(city);
	}
	
	public int getId() {
		return id;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setId(int id) {
		if(!numericValidator.isPositiveOrEqualToZero(id)) {
			throw new IllegalArgumentException("Id must be greater or equal than 0.");
		}
		
		this.id = id;
	}
	
	public void setCity(String city) {
		if(!stringValidator.isNullOrEmpty(city)) {
			throw new NullPointerException("City must have a value.");
		}
		
		this.city = city;
	}
	
	@Override
	public String toString() {
		return "Site [id=" + id + ", city=" + city + "]";
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(city, id);
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
		
		Site other = (Site) obj;
		return Objects.equals(city, other.city) && id == other.id;
	}
	
	
}
