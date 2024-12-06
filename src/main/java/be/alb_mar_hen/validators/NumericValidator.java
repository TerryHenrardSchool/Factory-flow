package be.alb_mar_hen.validators;

public class NumericValidator {
	public boolean isPositiveOrEqualToZero(int value) {
		return value >= 0;
	}
	public boolean isPositive(int value) {
		return value > 0;
	}
}
