package com.behnam.university.validation.validators;


import com.behnam.university.validation.annotations.ValidSevenDigits;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SevenDigitsValidator implements ConstraintValidator<ValidSevenDigits, Long> {
    @Override
    public boolean isValid(Long sevenDigits, ConstraintValidatorContext constraintValidatorContext) {
        if (sevenDigits != null)
            return sevenDigits.toString().length() == 7 && sevenDigits.toString().charAt(0) != '0';
        else return true;
    }
}
