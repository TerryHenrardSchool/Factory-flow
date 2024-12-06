package be.alb_mar_hen.validators;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateValidator {
	public boolean isInPast(LocalDate date) {
		return date.isBefore(LocalDate.now());
	}
}	
