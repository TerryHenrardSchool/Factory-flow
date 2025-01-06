package be.alb_mar_hen.validators;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public class DateValidator {
	public boolean isInPast(LocalDate date) {
		return date.isBefore(LocalDate.now());
	}
	
	public boolean isInPast(LocalDateTime date) {
		return date.isBefore(LocalDateTime.now());
	}
	
	public boolean isInPast(Optional<LocalDateTime> date) {	    
	    return  date.isEmpty() || (date.isPresent() && isInPast(date.get()));
	}

	public boolean isInFuture(LocalDate date) {
		return date.isAfter(LocalDate.now());
	}
	
	public boolean isInFuture(LocalDateTime date) {
		return date.isAfter(LocalDateTime.now());
	}
	
	public boolean isInFuture(Optional<LocalDateTime> date) {	    
	    return  date.isEmpty() || (date.isPresent() && isInFuture(date.get()));
	}
}	
