package be.alb_mar_hen.models;

import java.security.KeyStore.PrivateKeyEntry;
import java.time.LocalDate;
import java.util.Objects;

import be.alb_mar_hen.enumerations.MaintenanceStatus;
import be.alb_mar_hen.validators.DateValidator;
import be.alb_mar_hen.validators.NumericValidator;
import be.alb_mar_hen.validators.ObjectValidator;
import be.alb_mar_hen.validators.StringValidator;

public class Maintenance {
	private final static int MIN_LENGTH_REPORT = 150;
	
	private NumericValidator numericValidator;
	private StringValidator stringValidator;
	private ObjectValidator objectValidator;
	private DateValidator dateValidator;
	
	private int id;
	private LocalDate date;
	private int duration;
	private String report;
	private MaintenanceStatus status;
		
	public Maintenance(
		int id, 
		LocalDate date, 
		int duration, 
		String report, 
		MaintenanceStatus status,
		NumericValidator numericValidator,
		StringValidator stringValidator,
		ObjectValidator objectValidator,
		DateValidator dateValidator
	) {
		setDate(date);
		setDuration(duration);
		setReport(report);
		setStatus(status);
		this.numericValidator = numericValidator;
		this.stringValidator = stringValidator;
		this.objectValidator = objectValidator;
		this.dateValidator = dateValidator;
	}
	
	public Maintenance(
		LocalDate date, 
		int duration, 
		String report, 
		MaintenanceStatus status,
		NumericValidator numericValidator,
		StringValidator stringValidator,
		ObjectValidator objectValidator,
		DateValidator dateValidator
	) {
		this(0, date, duration, report, status, numericValidator, stringValidator, objectValidator, dateValidator);
	}

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

	@Override
	public String toString() {
		return "Maintenance [id=" + id + ", date=" + date + ", duration=" + duration + ", reportString=" + reportString
				+ ", status=" + status + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, duration, id, report, status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Maintenance other = (Maintenance) obj;
		return Objects.equals(date, other.date) && duration == other.duration && id == other.id
				&& Objects.equals(report, other.report) && Objects.equals(status, other.status);
	}
}
