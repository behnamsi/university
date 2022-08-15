package com.behnam.university.validation.validators;


import com.behnam.university.validation.annotations.ValidNationalId;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NationalIdValidator implements ConstraintValidator<ValidNationalId, Long> {

    @Override
    public boolean isValid(Long aLong, ConstraintValidatorContext constraintValidatorContext) {
        return aLong.toString().length() == 10 && aLong.toString().charAt(0) != '0';
    }
}
