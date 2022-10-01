package com.behnam.university.validation.validators;


import com.behnam.university.validation.annotations.ValidName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class NameValidator implements ConstraintValidator<ValidName, String> {
    private final Pattern pattern = Pattern.compile("^[a-zA-Z\\\\s]*$");

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        if (name != null)
            return name.matches(String.valueOf(pattern)) && name.length() >= 3 && name.length() <= 20;
        else return true;
    }
}
