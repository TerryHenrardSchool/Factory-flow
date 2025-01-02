package be.alb_mar_hen.models;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import be.alb_mar_hen.daos.EmployeeDAO;
import be.alb_mar_hen.daos.MachineDAO;
import be.alb_mar_hen.enumerations.MachineStatus;
import be.alb_mar_hen.validators.NumericValidator;
import be.alb_mar_hen.validators.ObjectValidator;
import be.alb_mar_hen.validators.StringValidator;

public class Machine {
	@JsonIgnoreProperties({"numericValidator", "objectValidator", "stringValidator"})
	// Validators
	NumericValidator numericValidator;
	ObjectValidator objectValidator;
	StringValidator stringValidator;
	
	// Attributes
	private Optional<Integer> id;
	private MachineStatus status;
	private String name;
	
	// Relations
	private Set<Maintenance> maintenances;
	private Set<Zone> zones;
	private MachineType machineType;
	
	// Constructors
	public Machine(
		Optional<Integer> id, 
		String type,
		MachineStatus status, 
		String name, 
		Zone zone,
		Optional<Integer> machineTypeId,
		String machineTypeName,
		double machineTypePrice,
		int machineTypeDaysBeforeMaintenance,
		NumericValidator numericValidator, 
		ObjectValidator objectValidator,
		StringValidator stringValidator
	) {
		this.numericValidator = numericValidator;
		this.objectValidator = objectValidator;
		this.stringValidator = stringValidator;
		maintenances = new HashSet<>();
		zones = new HashSet<>();
		addZone(zone);
		setId(id);
		setStatus(status);
		setName(name);
		this.machineType = new MachineType(
			machineTypeId, 
			machineTypeName, 
			machineTypePrice,
			machineTypeDaysBeforeMaintenance, 
			numericValidator, 
			stringValidator, 
			objectValidator
		);
	}
	
	public Machine(
		    Optional<Integer> id, 
		    String type,
		    MachineStatus status, 
		    String name, 
		    Zone zone,
		    Optional<Integer> machineTypeId,
		    String machineTypeName,
		    double machineTypePrice,
		    int machineTypeDaysBeforeMaintenance,
		    Set<Maintenance> maintenances,
		    Set<Zone> zones,
		    
		    NumericValidator numericValidator, 
		    ObjectValidator objectValidator,
		    StringValidator stringValidator
		) {
		    this.numericValidator = numericValidator;
		    this.objectValidator = objectValidator;
		    this.stringValidator = stringValidator;
		    this.maintenances = new HashSet<>(maintenances); 
		    this.zones = new HashSet<>(zones);
		    addZone(zone);
		    setId(id);
		    setStatus(status);
		    setName(name);
		    this.machineType = new MachineType(
		        machineTypeId, 
		        machineTypeName, 
		        machineTypePrice,
		        machineTypeDaysBeforeMaintenance, 
		        numericValidator, 
		        stringValidator, 
		        objectValidator
		    );
		}
	
	public Machine() {
	    this.maintenances = new HashSet<>();
	    this.zones = new HashSet<>();
	    this.numericValidator = new NumericValidator();  
        this.objectValidator = new ObjectValidator();  
        this.stringValidator = new StringValidator(); 
	}


	// Getters
	public Optional<Integer> getId() {
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
	public void setId(Optional<Integer> id) {
		if (!objectValidator.hasValue(id)) {
			throw new NullPointerException("Id must have a value.");
		}
		
	    if (!numericValidator.isPositiveOrEqualToZero(id)) {
	        throw new IllegalArgumentException("Id must be greater than or equal to 0");
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
		if(!stringValidator.hasValue(name)){
			throw new NullPointerException("Name must have a value.");
		}
		
		this.name = name;
	}
	
	public void setMaintenances(Set<Maintenance> maintenances) {
		if (!objectValidator.hasValue(maintenances)) {
			throw new NullPointerException("Maintenances must have a value.");
		}

		this.maintenances = maintenances;
	}
	
	// Methods
	public boolean addMaintenance(Maintenance maintenance) {
		if(!objectValidator.hasValue(maintenance)) {
			throw new NullPointerException("Maintenance must have value.");
		}
		
		boolean added = maintenances.add(maintenance);
		if (added) {
			maintenance.setMachine(this);
		}
		
		return added;
	}
	
	public boolean addZone(Zone zone) {
		if(!objectValidator.hasValue(zone)) {
			throw new NullPointerException("Zone must have a value.");
		}
		
		boolean added = zones.add(zone);
		
		return added;
	}

	// Override methods
	@Override
	public String toString() {
		return "Machine [id=" + id.orElse(null)+ 
			", status=" + status + 
			", name=" + name + 
			", maintenances=" + maintenances + 
			", zones=" + zones + 
			", machineType=" + machineType + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(
			id.orElse(0),
			machineType,
			maintenances, 
			name,
			status,
			zones
		);
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
		
		Machine other = (Machine) obj;
		return Objects.equals(id.orElse(0), other.id.orElse(0))
			&& Objects.equals(machineType, other.machineType)
			&& Objects.equals(maintenances, other.maintenances) 
			&& Objects.equals(name, other.name)
			&& status == other.status
			&& Objects.equals(zones, other.zones);
	}
	
	public static List<Machine> findAll() {
        MachineDAO machineDAO = new MachineDAO();
        List<Machine> machines = machineDAO.findAll();
        
        return machines;
    }
		
}
