package be.alb_mar_hen.validators;

import java.util.regex.Pattern;

public class StringValidator {
	public boolean matchRegEx(String regEx, String string) {
		return Pattern.matches(regEx, string);
	}
}
