package com.behnam.university.validation.validators;


import com.behnam.university.validation.annotations.ValidNationalId;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NationalIdValidator implements ConstraintValidator<ValidNationalId, Long> {

    @Override
    public boolean isValid(Long nationalId, ConstraintValidatorContext constraintValidatorContext) {
        if (nationalId != null)
            return nationalId.toString().length() == 10 && nationalId.toString().charAt(0) != '0';
        else return true;
    }
}
