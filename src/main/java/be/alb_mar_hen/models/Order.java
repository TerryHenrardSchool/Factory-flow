package be.alb_mar_hen.models;

import java.time.LocalDateTime;
import java.util.Objects;

import be.alb_mar_hen.validators.DateValidator;
import be.alb_mar_hen.validators.NumericValidator;
import be.alb_mar_hen.validators.ObjectValidator;

public class Order {
	// Validators
	private NumericValidator numericValidator;
	private DateValidator dateValidator;
	private ObjectValidator objectValidator;
	
	// Attributes
	private int id;
	private LocalDateTime orderDateTime;
	private double price;
	
	// Relations
	private Supplier supplier;
	private PurchasingAgent purchasingAgent;
	private Machine machine;
	
	// Constructors
	public Order(
		int id, 
		LocalDateTime orderDate, 
		double price,
		Supplier supplier,
		PurchasingAgent purchasingAgent,
		Machine machine,
		NumericValidator numericValidator,
		DateValidator dateValidator,
		ObjectValidator objectValidator
	) {
		this.numericValidator = numericValidator;
		this.dateValidator = dateValidator;
		this.objectValidator = objectValidator;
		setId(id);
		setOrder(orderDate);
		setPrice(price);
		setMachine(machine);
		setPurchasingAgent(purchasingAgent);
		setSupplier(supplier);
	}
	
	public Order(
		LocalDateTime orderDate, 
		double price,
		Supplier supplier,
		PurchasingAgent purchasingAgent,
		Machine machine,
		NumericValidator numericValidator,
		DateValidator dateValidator,
		ObjectValidator objectValidator
	) {
		this(0, orderDate, price, supplier, purchasingAgent, machine ,numericValidator, dateValidator, objectValidator);
	}
	
	// Getters
	public int getId() {
		return id;
	}
	
	public LocalDateTime getOrder() {
		return orderDateTime;
	}
	
	public double getPrice() {
		return price;
	}
	
	public Supplier getSupplier() {
		return supplier;
	}
	
	public PurchasingAgent getPurchasingAgent() {
		return purchasingAgent;
	}
	
	public Machine getMachine() {
		return machine;
	}
	
	// Setters
	public void setId(int id) {
		if(!numericValidator.isPositiveOrEqualToZero(id)) {
			throw new IllegalArgumentException("Id must be greater or equals than zero.");
		}
		
		this.id = id;
	}
	
	public void setOrder(LocalDateTime orderDate) {
		if(!objectValidator.hasValue(orderDate)) {
			throw new NullPointerException("The orderDate must have a value.");
		}
		
		if(!dateValidator.isInPast(orderDate)) {
			throw new IllegalArgumentException("The orderDate cannot be in the future.");
		}
		
		this.orderDateTime = orderDate;
	}
	
	public void setPrice(double price) {
		if(numericValidator.isPositive(price)) {
			throw new IllegalArgumentException("Price must be positive.");
		}
		
		this.price = price;
	}
	
	public void setMachine(Machine machine) {
		if(!objectValidator.hasValue(machine)) {
			throw new NullPointerException("Machine must have a value.");
		}
		
		this.machine = machine;
	}
	
	public void setPurchasingAgent(PurchasingAgent purchasingAgent) {
		if(!objectValidator.hasValue(purchasingAgent)) {
			throw new NullPointerException("Purchasing agent must have a value.");
		}
		
		this.purchasingAgent = purchasingAgent;
	}
	
	public void setSupplier(Supplier supplier) {
		if(objectValidator.hasValue(supplier)) {
			throw new NullPointerException("Supplier must have a value.");
		}
		
		this.supplier = supplier;
	}

	// Override methods
	@Override
	public String toString() {
		return "Order [id=" + id + ", orderDateTime=" + orderDateTime + ", price=" + price + ", supplier=" + supplier
				+ ", purchasingAgent=" + purchasingAgent + ", machine=" + machine + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, machine, orderDateTime, price, purchasingAgent, supplier);
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
		
		Order other = (Order) obj;
		return id == other.id 
			&& Objects.equals(machine, other.machine)
			&& Objects.equals(orderDateTime, other.orderDateTime)
			&& Double.doubleToLongBits(price) == Double.doubleToLongBits(other.price)
			&& Objects.equals(purchasingAgent, other.purchasingAgent) 
			&& Objects.equals(supplier, other.supplier);
	}
	
	
}