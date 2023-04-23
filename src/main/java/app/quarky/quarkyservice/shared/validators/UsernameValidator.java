package app.quarky.quarkyservice.shared.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<Username, String> {


    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) return false;
        var actualUsername = value.trim();
        var length = actualUsername.length();
        return length >= 3 && length <= 20;
    }
}
