package be.alb_mar_hen.models;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import be.alb_mar_hen.validators.NumericValidator;
import be.alb_mar_hen.validators.ObjectValidator;

public class Zone {
	private NumericValidator numericValidator;
	private ObjectValidator objectValidator;
	
	private int id;
	private String color;
	private String name;
	private Set<Machine> machines;
	private Site site;
	
	public Zone(
			NumericValidator numericValidator, 
			ObjectValidator objectValidator, 
			int id, 
			String color, 
			String name,
			Machine machine,
			Site site
	) {
		this.numericValidator = numericValidator;
		this.objectValidator = objectValidator;
		machines = new HashSet<>();
		addMachine(machine);
		setId(id);
		setColor(color);
		setName(name);
		setSite(site);
	}
	
	public Zone(
			NumericValidator numericValidator, 
			ObjectValidator objectValidator, 
			String color, 
			String name,
			Machine machine,
			Site site
	) {
		this(numericValidator, objectValidator, 0, color, name, machine, site);
	}

	public int getId() {
		return id;
	}

	public String getColor() {
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

	public void setId(int id) {
		if(!numericValidator.isPositiveOrEqualToZero(id)) {
			throw new IllegalArgumentException("The id must be greater or equal than 0.");
		}
		
		this.id = id;
	}

	public void setColor(String color) {
		if(!objectValidator.hasValue(color)) {
			throw new NullPointerException("Color must have a value.");
		}
		
		this.color = color;
	}

	public void setName(String name) {
		if(!objectValidator.hasValue(name)) {
			throw new NullPointerException("Name must have a value.");
		}
		
		this.name = name;
	}

	public void setSite(Site site) {
		if(!objectValidator.hasValue(site)) {
			throw new NullPointerException("Site must have a value.");
		}
		
		this.site = site;
	}
	
	public boolean addMachine(Machine machine) {
		if(!objectValidator.hasValue(machine)) {
			throw new NullPointerException("Machine must have a value.");
		}
		
		return machines.add(machine);
	}

	@Override
	public String toString() {
		return "Zone [id=" + id + ", color=" + color + ", name=" + name + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(color, id, machines, name, site);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Zone other = (Zone) obj;
		return Objects.equals(color, other.color) && id == other.id && Objects.equals(machines, other.machines)
				&& Objects.equals(name, other.name) && Objects.equals(site, other.site);
	}
}
