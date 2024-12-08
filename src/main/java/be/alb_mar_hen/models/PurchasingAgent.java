package be.alb_mar_hen.models;

import java.util.HashSet;
import java.util.Set;

import be.alb_mar_hen.formatters.StringFormatter;
import be.alb_mar_hen.validators.NumericValidator;
import be.alb_mar_hen.validators.ObjectValidator;
import be.alb_mar_hen.validators.StringValidator;

public class PurchasingAgent extends Employee{
	
	// Validators
	private ObjectValidator objectValidator;
	
	// Relations
	private Set<Order> orders;
	
	// Constructors
	public PurchasingAgent(
		int id, 
		String matricule, 
		String password, 
		String firstName, 
		String lastName,
		StringValidator stringValidator, 
		NumericValidator numericValidator,
		ObjectValidator objectValidator,
		StringFormatter stringFormatter
	) {                  
		super(id, matricule, password, firstName, lastName, stringValidator, numericValidator, stringFormatter);
		this.objectValidator = objectValidator;
		this.orders = new HashSet<>();
	}

	public PurchasingAgent(
		String matricule, 
		String password, 
		String firstName, 
		String lastName, 
		StringValidator stringValidator, 
		NumericValidator numericValidator,
		ObjectValidator objectValidator,
		StringFormatter stringFormatter
	) {
		super(0, matricule, password, firstName, lastName, stringValidator, numericValidator, stringFormatter);
	}
	
	// Getters
	public Set<Order> getOrders() {
		return orders;
	}
	
	// Methods
	public boolean addOrder(Order order) {
		if (!objectValidator.hasValue(order)) {
			throw new IllegalArgumentException("Order must have a value.");
		}

		boolean added = orders.add(order);
		if (added) {
			order.setPurchasingAgent(this);
		}
		return added;
	}
	
	// Override methods
	@Override
	public String toString() {
		return super.toString() + "PurchasingWorker [orders=" + orders + "]";
	}
	
	@Override
    public boolean equals(Object object) {
    	if(!(object instanceof Employee)) {
    		return false;
    	}
    	
    	return super.equals((Employee) object) 
			&& orders.equals(((PurchasingAgent) object).getOrders());
    }
	
	@Override
	public int hashCode() {
		return super.hashCode() + orders.hashCode();
	}
}