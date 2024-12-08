package be.alb_mar_hen.models;

import java.util.Objects;

import be.alb_mar_hen.formatters.StringFormatter;
import be.alb_mar_hen.validators.NumericValidator;
import be.alb_mar_hen.validators.StringValidator;

public abstract class Employee {
	
	// Constants
	private final static String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
	private final static String NAME_REGEX = "/^[\\p{L}'][ \\p{L}'-]*[\\p{L}]$/u";
	
	// Validators
	private StringValidator stringValidator; 
	private NumericValidator numericValidator;
	
	// Formatters
	private StringFormatter stringFormatter;
	
	// Attributes
	private int id;
	private String matricule;
	private String password;
	private String firstName;
	private String lastName;
	
	// Constructors
	public Employee(
		int id, 
		String matricule, 
		String password, 
		String firstName, 
		String lastName, 
		StringValidator stringValidator, 
		NumericValidator numericValidator,
		StringFormatter stringFormatter
	) {
		this.stringValidator = stringValidator;
		this.numericValidator = numericValidator;
		this.stringFormatter = stringFormatter;
		setId(id);
		setMatricule(matricule);
		setPassword(password);
		setFirstName(firstName);
		setLastName(lastName);
	}
	
	public Employee(
		String matricule, 
		String password, 
		String firstName, 
		String lastName, 
		StringValidator stringValidator, 
		NumericValidator numericValidator,
		StringFormatter stringFormatter
	) {
		this(0, matricule, password, firstName, lastName, stringValidator, numericValidator, stringFormatter);
	}
	
	// Getters
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
	
	// Setters
	public void setId(int id){
		if(!numericValidator.isPositiveOrEqualToZero(id)) {
			throw new IllegalArgumentException("Id must be greater than 0");
		}
		
		this.id = id;
	}
	
	public void setMatricule(String matricule) {
		if(!stringValidator.isNullOrEmpty(matricule)) {			
			throw new NullPointerException("Matricule must have a value.");
		}
		
		this.matricule = matricule;
	}
	
	public void setPassword(String password) {
		if(!stringValidator.isNullOrEmpty(password)) {
			throw new NullPointerException("The password must have a value.");
		}
		
		if(!stringValidator.matchRegEx(PASSWORD_REGEX, password)) {
			throw new IllegalArgumentException("Password must have at least 8 characters, contains at least a number and at least a letter.");
		}
		
		this.password = password;
	}
	
	public void setFirstName(String firstName) {
		if(!stringValidator.isNullOrEmpty(firstName)) {
			throw new NullPointerException("The first name must have a value.");
		}
		
		if(!stringValidator.matchRegEx(NAME_REGEX, firstName)) {
			throw new IllegalArgumentException("The first name is incorrect.");
		}
		
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName) {
		if(!stringValidator.isNullOrEmpty(lastName)) {
			throw new NullPointerException("The last name must have a value.");
		}
		
		if(!stringValidator.matchRegEx(NAME_REGEX, lastName)) {
			throw new IllegalArgumentException("The last name is incorrect.");
		}
		
		this.lastName = lastName;
	}
	
	// Private methods
	private String getFirstNameFormatted() {
		return stringFormatter.firstToUpper(firstName);
	}
	
	private String getLastNameFormatted() {
		return lastName.toUpperCase();
	}
	
	// Public methods
	public String getFullNameFormatted() {
		return getLastNameFormatted() + " " + getFirstNameFormatted();
	}

	// Override methods
	@Override
	public String toString() {
		return "Person [id=" + id + ", matricule=" + matricule + ", password=" + password + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(firstName, id, lastName, matricule, password);
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
		
		Employee other = (Employee) obj;
		return Objects.equals(firstName, other.firstName) 
			&& id == other.id 
			&& Objects.equals(lastName, other.lastName)
			&& Objects.equals(matricule, other.matricule) 
			&& Objects.equals(password, other.password);
	}
}
