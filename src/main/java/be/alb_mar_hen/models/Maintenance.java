package be.alb_mar_hen.models;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import be.alb_mar_hen.enumerations.MaintenanceStatus;
import be.alb_mar_hen.validators.DateValidator;
import be.alb_mar_hen.validators.NumericValidator;
import be.alb_mar_hen.validators.ObjectValidator;
import be.alb_mar_hen.validators.StringValidator;

public class Maintenance {
	// Constants
	private final static int MIN_LENGTH_REPORT = 150;
	
	// Validators
	private NumericValidator numericValidator;
	private StringValidator stringValidator;
	private ObjectValidator objectValidator;
	private DateValidator dateValidator;
	
	// Attributes
	private int id;
	private LocalDate date;
	private int duration;
	private String report;
	private MaintenanceStatus status;
	
	// Relations
	private Set<MaintenanceWorker> maintenanceWorkers;
	private Machine machine;
	private MaintenanceResponsable maintenanceResponsable;
		
	// Constructors
	public Maintenance(
		int id, 
		LocalDate date, 
		int duration, 
		String report, 
		MaintenanceStatus status,
		Machine machine, 
		MaintenanceWorker maintenanceWorker,
		MaintenanceResponsable maintenanceResponsable,
		NumericValidator numericValidator,
		StringValidator stringValidator,
		ObjectValidator objectValidator,
		DateValidator dateValidator
	) {
		this.numericValidator = numericValidator;
		this.stringValidator = stringValidator;
		this.objectValidator = objectValidator;
		this.dateValidator = dateValidator;
		maintenanceWorkers = new HashSet<>();
		setDate(date);
		setDuration(duration);
		setReport(report);
		setStatus(status);
		setMachine(machine);
		setMaintenanceResponsable(maintenanceResponsable);
		addMaintenanceWorker(maintenanceWorker);
	}
	
	public Maintenance(
		LocalDate date, 
		int duration, 
		String report, 
		MaintenanceStatus status,
		Machine machine, 
		MaintenanceWorker maintenanceWorker,
		MaintenanceResponsable maintenanceResponsable,
		NumericValidator numericValidator,
		StringValidator stringValidator,
		ObjectValidator objectValidator,
		DateValidator dateValidator
	) {
		this(
			0, 
			date, 
			duration, 
			report, 
			status,
			machine,
			maintenanceWorker,
			maintenanceResponsable,
			numericValidator, 
			stringValidator,
			objectValidator,
			dateValidator
		);
	}

	// Getters
	public int getId() {
		return id;
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public String getReport() {
		return report;
	}
	
	public MaintenanceStatus getStatus() {
		return status;
	}
	
	public Machine getMachine() {
		return machine;
	}
	
	public MaintenanceResponsable getMaintenanceResponsable() {
		return maintenanceResponsable;
	}
	
	public Set<MaintenanceWorker> getMaintenanceWorkers() {
		return maintenanceWorkers;
	}
	
	// Setters
	public void setId(int id) {
		if(!numericValidator.isPositiveOrEqualToZero(id)) {
			throw new IllegalArgumentException("Id must be greater than 0");
		}
		
		this.id = id;
	}
	
	public void setDate(LocalDate date) {
		if(!objectValidator.hasValue(date)) {
			throw new NullPointerException("Date must have a value.");
		}
		
		if(!dateValidator.isInPast(date)) {
			throw new IllegalArgumentException("Date must be in the past.");
		}
		
		this.date = date;
	}
	
	public void setDuration(int duration) {
		if(!numericValidator.isPositive(duration)) {
			throw new IllegalArgumentException("Duration must be positive.");
		}
		
		this.duration = duration;
	}
	
	public void setReport(String report) {
		if(!objectValidator.hasValue(report)) {
			throw new NullPointerException("Report must have a value.");
		}
		
		if(!stringValidator.isLongerOrEqualsThan(report, MIN_LENGTH_REPORT)) {
			throw new IllegalArgumentException("Report must be at least " + MIN_LENGTH_REPORT + " long.");
		}
		
		this.report = report;
	}
	
	public void setStatus(MaintenanceStatus status) {
		if(!objectValidator.hasValue(status)) {
			throw new NullPointerException("Status must have a value.");
		}
		
		this.status = status;
	}

	public void setMachine(Machine machine) {
		if(!objectValidator.hasValue(machine)) {
			throw new NullPointerException("Machine must have a value.");
		}
		
		if (this.machine != machine) {
			this.machine = machine;
			machine.addMaintenance(this);
		}
		
		this.machine = machine;
	}
	
	public void setMaintenanceResponsable(MaintenanceResponsable responsable) {
	    if (!objectValidator.hasValue(responsable)) {
	        throw new NullPointerException("Responsable must have a value.");
	    }

	    if (this.maintenanceResponsable != responsable) {
	        this.maintenanceResponsable = responsable;
	        responsable.addMaintenance(this);
	    }
	}
	
	// Methods
	public boolean addMaintenanceWorker(MaintenanceWorker worker) {
	    if (!objectValidator.hasValue(worker)) {
	        throw new NullPointerException("Worker must have a value.");
	    }

	    boolean added = maintenanceWorkers.add(worker);
	    if (added) {
	        worker.addMaintenance(this);
	    }

	    return added;
	}
	
	//Override methods
	@Override
	public String toString() {
		return "Maintenance [id=" + id + ", date=" + date + ", duration=" + duration + ", reportString=" + report + ", status=" + status + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, duration, id, report, status);
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
		
		Maintenance other = (Maintenance) obj;
		return Objects.equals(date, other.date) 
			&& duration == other.duration 
			&& id == other.id
			&& Objects.equals(report, other.report) 
			&& Objects.equals(status, other.status);
	}
}
