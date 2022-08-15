package com.behnam.university.validation.validators;



import com.behnam.university.validation.annotations.ValidName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<ValidName, String> {

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        return name.matches("^[a-zA-Z\\\\s]*$") && name.length() >= 3 && name.length() <= 20;
    }
}
