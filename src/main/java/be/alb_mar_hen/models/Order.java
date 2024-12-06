package be.alb_mar_hen.models;

import java.time.LocalDateTime;

import be.alb_mar_hen.validators.DateValidator;
import be.alb_mar_hen.validators.NumericValidator;
import be.alb_mar_hen.validators.ObjectValidator;

public class Order {
	private NumericValidator numericValidator;
	private DateValidator dateValidator;
	private ObjectValidator objectValidator;
	
	private int id;
	private LocalDateTime orderDate;
	private double price;
	private Supplier supplier;
	private PurchasingAgent purchasingAgent;
	private Machine machine;
	
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
		setId(id);
		setOrder(orderDate);
		setPrice(price);
		setMachine(machine);
		setPurchasingAgent(purchasingAgent);
		setSupplier(supplier);
		this.numericValidator = numericValidator;
		this.dateValidator = dateValidator;
		this.objectValidator = objectValidator;
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
	
	public int getId() {
		return id;
	}
	
	public LocalDateTime getOrder() {
		return orderDate;
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
		
		this.orderDate = orderDate;
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
}