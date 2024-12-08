package be.alb_mar_hen.validators;

import java.util.regex.Pattern;

public class StringValidator {
	public boolean matchRegEx(String regEx, String string) {
		return Pattern.matches(regEx, string);
	}
	
	public boolean isLongerOrEqualsThan(String string, int min) {
		return string.length() >= min; 
	}
	
	public boolean isNullOrEmpty(String string) {
		return string == null || string.isEmpty();
	}
}
