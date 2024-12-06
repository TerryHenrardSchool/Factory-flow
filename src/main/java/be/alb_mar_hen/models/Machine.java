package be.alb_mar_hen.models;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import be.alb_mar_hen.enumerations.MachineStatus;
import be.alb_mar_hen.validators.NumericValidator;
import be.alb_mar_hen.validators.ObjectValidator;

public class Machine {
	NumericValidator numericValidator;
	ObjectValidator objectValidator;
	
	private int id;
	private String type;
	private MachineStatus status;
	private String name;
	private Set<Maintenance> maintenances;
	private Set<Zone> zones;
	
	public Machine(
		int id, 
		String type,
		MachineStatus status, 
		String name, 
		Zone zone,
		NumericValidator numericValidator, 
		ObjectValidator objectValidator
	) {
		maintenances = new HashSet<>();
		zones = new HashSet<>();
		addZone(zone);
		this.numericValidator = numericValidator;
		this.objectValidator = objectValidator;
		this.id = id;
		this.type = type;
		this.status = status;
		this.name = name;
	}
	
	public Machine( 
		String type,
		MachineStatus status, 
		String name, 
		Zone zone,
		NumericValidator numericValidator, 
		ObjectValidator objectValidator
	) {
		this(0, type, status, name, zone,numericValidator, objectValidator);
	}

	public int getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public MachineStatus getStatus() {
		return status;
	}

	public String getName() {
		return name;
	}

	public void setId(int id) {
		if(!numericValidator.isPositiveOrEqualToZero(id)) {
			throw new IllegalArgumentException("Id must be greater or equal than zero.");
		}
		
		this.id = id;
	}

	public void setType(String type) {
		if(!objectValidator.hasValue(type)) {
			throw new NullPointerException("Type must have a value.");
		}
		
		this.type = type;
	}

	public void setStatus(MachineStatus status) {
		if(!objectValidator.hasValue(status)) {
			throw new NullPointerException("Status must have a value.");
		}
		
		this.status = status;
	}

	public void setName(String name) {
		if(!objectValidator.hasValue(name)){
			throw new NullPointerException("Name must have a value.");
		}
		
		this.name = name;
	}
	
	public boolean addMaintenance(Maintenance maintenance) {
		if(!objectValidator.hasValue(maintenance)) {
			throw new NullPointerException("Maintenance must have value.");
		}
		
		return maintenances.add(maintenance);
	}
	
	public boolean addZone(Zone zone) {
		if(!objectValidator.hasValue(zone)) {
			throw new NullPointerException("Zone must have a value.");
		}
		
		return zones.add(zone);
	}
	
	//Override Methods
	@Override
	public String toString() {
		return "Machine [id=" + id + ", type=" + type + ", status=" + status + ", name=" + name + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, status, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Machine other = (Machine) obj;
		return id == other.id && Objects.equals(name, other.name) && status == other.status
				&& Objects.equals(type, other.type);
	}
}
