package be.alb_mar_hen.models;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import be.alb_mar_hen.enumerations.ZoneColor;
import be.alb_mar_hen.validators.NumericValidator;
import be.alb_mar_hen.validators.ObjectValidator;
import be.alb_mar_hen.validators.StringValidator;

public class Zone {
	// Validators
	private NumericValidator numericValidator;
	private ObjectValidator objectValidator;
	private StringValidator stringValidator;
	
	// Attributes
	private int id;
	private ZoneColor color;
	private String name;
	
	// Relations
	private Set<Machine> machines;
	private Site site;
	
	// Constructors
	public Zone(
		int id, 
		ZoneColor color, 
		String name,
		Machine machine,
		int siteId,
		String siteName,
		NumericValidator numericValidator, 
		ObjectValidator objectValidator,
		StringValidator stringValidator
	) {
		this.numericValidator = numericValidator;
		this.stringValidator = stringValidator;
		this.objectValidator = objectValidator;
		machines = new HashSet<>();
		addMachine(machine);
		setId(id);
		setColor(color);
		setName(name);
		this.site = new Site(siteId, siteName, stringValidator, numericValidator);
	}
	
	public Zone(
		ZoneColor color, 
		String name,
		Machine machine,
		int siteId,
		String siteName,
		NumericValidator numericValidator, 
		ObjectValidator objectValidator,
		StringValidator stringValidator
	) {
		this(0, color, name, machine, siteId, siteName, numericValidator, objectValidator, stringValidator);
	}

	// Getters
	public int getId() {
		return id;
	}
	
	public ZoneColor getColor() {
		return color;
	}
	
	public String getName() {
		return name;
	}
	
	public Set<Machine> getMachines() {
		return machines;
	}
	
	public Site getSite() {
        return site;
	}
	
	// Setters
	public void setId(int id) {
		if(!numericValidator.isPositiveOrEqualToZero(id)) {
			throw new IllegalArgumentException("The id must be greater or equal than 0.");
		}
		
		this.id = id;
	}

	public void setColor(ZoneColor color) {
		if(!objectValidator.hasValue(color)) {
			throw new NullPointerException("Color must have a value.");
		}
		
		this.color = color;
	}

	public void setName(String name) {
		if(!stringValidator.hasValue(name)) {
			throw new NullPointerException("Name must have a value.");
		}
		
		this.name = name;
	}
	
	// Methods
	public boolean addMachine(Machine machine) {
		if(!objectValidator.hasValue(machine)) {
			throw new NullPointerException("Machine must have a value.");
		}
		
		boolean added = machines.add(machine);
		if (added) {
			machine.addZone(this);
		}
		
		return added;
	}

	// Override methods
	@Override
	public String toString() {
		return "Zone [id=" + id + ", color=" + color + ", name=" + name + ", machines=" + machines + ", site=" + site + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(color, id, machines, name, site);
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
		
		Zone other = (Zone) obj;
		return color == other.color 
			&& id == other.id 
			&& Objects.equals(machines, other.machines)
			&& Objects.equals(name, other.name) 
			&& Objects.equals(site, other.site);
	}
}
