package be.alb_mar_hen.models;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import be.alb_mar_hen.enumerations.MachineStatus;
import be.alb_mar_hen.validators.NumericValidator;
import be.alb_mar_hen.validators.ObjectValidator;
import be.alb_mar_hen.validators.StringValidator;

public class Machine {
	// Validators
	NumericValidator numericValidator;
	ObjectValidator objectValidator;
	StringValidator stringValidator;
	
	// Attributes
	private int id;
	private MachineStatus status;
	private String name;
	
	// Relations
	private Set<Maintenance> maintenances;
	private Set<Zone> zones;
	private MachineType machineType;
	
	// Constructors
	public Machine(
		int id, 
		String type,
		MachineStatus status, 
		String name, 
		Zone zone,
		int machineTypeId,
		String machineTypeName,
		double machineTypePrice,
		int machineTypeDaysBeforeMaintenance,
		NumericValidator numericValidator, 
		ObjectValidator objectValidator,
		StringValidator stringValidator
	) {
		maintenances = new HashSet<>();
		zones = new HashSet<>();
		addZone(zone);
		this.numericValidator = numericValidator;
		this.objectValidator = objectValidator;
		this.stringValidator = stringValidator;
		setId(id);
		setStatus(status);
		setName(name);
		this.machineType = new MachineType(
			machineTypeId, 
			machineTypeName, 
			machineTypePrice,
			machineTypeDaysBeforeMaintenance, 
			numericValidator, 
			stringValidator
		);
	}
	
	public Machine( 
		String type,
		MachineStatus status, 
		String name, 
		Zone zone,
		int machineTypeId,
		String machineTypeName,
		double machineTypePrice,
		int machineTypeDaysBeforeMaintenance,
		NumericValidator numericValidator, 
		ObjectValidator objectValidator,
		StringValidator stringValidator
	) {
		this(
			0, 
			type, 
			status, 
			name, 
			zone, 
			machineTypeId, 
			machineTypeName, 
			machineTypePrice, 
			machineTypeDaysBeforeMaintenance, 
			numericValidator, 
			objectValidator, 
			stringValidator
		);
	}

	// Getters
	public int getId() {
		return id;
	}

	public MachineStatus getStatus() {
		return status;
	}

	public String getName() {
		return name;
	}
	
	public Set<Maintenance> getMaintenances() {
		return maintenances;
	}
	
	public Set<Zone> getZones() {
		return zones;
	}
	
	public MachineType getMachineType() {
		return machineType;
	}

	// Setters
	public void setId(int id) {
		if(!numericValidator.isPositiveOrEqualToZero(id)) {
			throw new IllegalArgumentException("Id must be greater or equal than zero.");
		}
		
		this.id = id;
	}

	public void setStatus(MachineStatus status) {
		if(!objectValidator.hasValue(status)) {
			throw new NullPointerException("Status must have a value.");
		}
		
		this.status = status;
	}

	public void setName(String name) {
		if(!stringValidator.isNullOrEmpty(name)){
			throw new NullPointerException("Name must have a value.");
		}
		
		this.name = name;
	}
	
	// Methods
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

	@Override
	public String toString() {
		return "Machine [numericValidator=" + numericValidator + ", objectValidator=" + objectValidator
				+ ", stringValidator=" + stringValidator + ", id=" + id + ", status=" + status + ", name=" + name
				+ ", maintenances=" + maintenances + ", zones=" + zones + ", machineType=" + machineType + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, machineType, maintenances, name, numericValidator, objectValidator, status, stringValidator, zones);
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
		
		Machine other = (Machine) obj;
		return id == other.id && Objects.equals(machineType, other.machineType)
			&& Objects.equals(maintenances, other.maintenances) 
			&& Objects.equals(name, other.name)
			&& Objects.equals(numericValidator, other.numericValidator)
			&& Objects.equals(objectValidator, other.objectValidator) 
			&& status == other.status
			&& Objects.equals(stringValidator, other.stringValidator) 
			&& Objects.equals(zones, other.zones);
	}
		
}
