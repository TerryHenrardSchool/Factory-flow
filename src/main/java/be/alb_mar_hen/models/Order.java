package be.alb_mar_hen.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import be.alb_mar_hen.daos.MachineDAO;
import be.alb_mar_hen.daos.OrderDAO;
import be.alb_mar_hen.utils.CustomDateDeserializer;
import be.alb_mar_hen.validators.DateValidator;
import be.alb_mar_hen.validators.NumericValidator;
import be.alb_mar_hen.validators.ObjectValidator;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "keyOrder")
public class Order {
	
	// Validators
	private NumericValidator numericValidator;
	private DateValidator dateValidator;
	private ObjectValidator objectValidator;
	
	// Attributes
	@JsonDeserialize(using = CustomDateDeserializer.class)
	@JsonProperty("order")
	private LocalDateTime orderDateTime;
	
	// Relations
	private Supplier supplier;
	private PurchasingAgent purchasingAgent;
	private Machine machine;
	
	// Constructors
	public Order( 
		LocalDateTime orderDate, 
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
		setOrder(orderDate);
		setMachine(machine);
		setPurchasingAgent(purchasingAgent);
		setSupplier(supplier);
	}
	
	public Order() {
		this.numericValidator = new NumericValidator();
		this.dateValidator = new DateValidator();
		this.objectValidator = new ObjectValidator();
	}
	
	// Getters
	public LocalDateTime getOrder() {
		return orderDateTime;
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
	public void setOrder(LocalDateTime orderDate) {
		if(!objectValidator.hasValue(orderDate)) {
			throw new NullPointerException("The orderDate must have a value.");
		}
		
		if(!dateValidator.isInPast(orderDate)) {
			throw new IllegalArgumentException("The orderDate cannot be in the future.");
		}
		
		this.orderDateTime = orderDate;
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
		if(!objectValidator.hasValue(supplier)) {
			throw new NullPointerException("Supplier must have a value.");
		}
		
		this.supplier = supplier;
	}
	
	// Methods
	public static List<Order> findAll() {
        OrderDAO orderDAO = new OrderDAO();
        List<Order> orders = orderDAO.findAll();
        
        return orders;
    }

	// Override methods
	@Override
	public String toString() {
		return 
			"orderDateTime=" + orderDateTime + 
			", supplier=" + supplier + 
			", purchasingAgent=" + purchasingAgent + 
			", machine=" + machine + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(machine, orderDateTime, purchasingAgent, supplier);
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
		
		Order other = (Order) obj;
		return Objects.equals(machine, other.machine)
			&& Objects.equals(orderDateTime, other.orderDateTime)
			&& Objects.equals(purchasingAgent, other.purchasingAgent) 
			&& Objects.equals(supplier, other.supplier);
	}
}