package be.alb_mar_hen.models;

import java.util.Objects;

import be.alb_mar_hen.validators.NumericValidator;
import be.alb_mar_hen.validators.ObjectValidator;
import be.alb_mar_hen.validators.StringValidator;

public abstract class Person {
	private final static String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
	private final static String NAME_REGEX = "/^[\\p{L}'][ \\p{L}'-]*[\\p{L}]$/u";
	
	private ObjectValidator objectValidator;
	private StringValidator stringValidator; 
	private NumericValidator numericValidator;
	
	private int id;
	private String matricule;
	private String password;
	private String firstName;
	private String lastName;
	
	public Person(
		int id, 
		String matricule, 
		String password, 
		String firstName, 
		String lastName, 
		ObjectValidator ObjectValidator, 
		StringValidator stringValidator, 
		NumericValidator numericValidator
	) {
		setId(id);
		setMatricule(matricule);
		setPassword(password);
		setFirstName(firstName);
		setLastName(lastName);
		this.objectValidator = ObjectValidator;
		this.stringValidator = stringValidator;
		this.numericValidator = numericValidator;
	}
	
	public Person(
			String matricule, 
			String password, 
			String firstName, 
			String lastName, 
			ObjectValidator objectValidator, 
			StringValidator stringValidator, 
			NumericValidator numericValidator
	) {
		this(0, matricule, password, firstName, lastName, objectValidator, stringValidator, numericValidator);
	}
	
	public int getId() {
		return id;
	}
	public String getMatricule() {
		return matricule;
	}
	public String getPassword() {
		return password;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setId(int id){
		if(!numericValidator.isPositiveOrEqualToZero(id)) {
			throw new IllegalArgumentException("Id must be greater than 0");
		}
		
		this.id = id;
	}
	
	public void setMatricule(String matricule) {
		if(!objectValidator.hasValue(matricule)) {			
			throw new NullPointerException("Matricule must have a value.");
		}
		
		this.matricule = matricule;
	}
	
	public void setPassword(String password) {
		if(!objectValidator.hasValue(password)) {
			throw new NullPointerException("The password must have a value.");
		}
		
		if(!stringValidator.matchRegEx(PASSWORD_REGEX, password)) {
			throw new IllegalArgumentException("Password must have at least 8 characters, contains at least a number and at least a letter.");
		}
		
		this.password = password;
	}
	
	public void setFirstName(String firstName) {
		if(!objectValidator.hasValue(firstName)) {
			throw new NullPointerException("The first name must have a value.");
		}
		
		if(!stringValidator.matchRegEx(NAME_REGEX, firstName)) {
			throw new IllegalArgumentException("The first name is incorrect.");
		}
		
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName) {
		if(!objectValidator.hasValue(lastName)) {
			throw new NullPointerException("The last name must have a value.");
		}
		
		if(!stringValidator.matchRegEx(NAME_REGEX, lastName)) {
			throw new IllegalArgumentException("The last name is incorrect.");
		}
		
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", matricule=" + matricule + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(firstName, id, lastName, matricule, password);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		return Objects.equals(firstName, other.firstName) && id == other.id && Objects.equals(lastName, other.lastName)
				&& Objects.equals(matricule, other.matricule) && Objects.equals(password, other.password);
	}
}
