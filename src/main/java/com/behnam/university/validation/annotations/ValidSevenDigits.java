package com.behnam.university.validation.annotations;


import com.behnam.university.validation.validators.SevenDigitsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SevenDigitsValidator.class)
public @interface ValidSevenDigits {
    String message() default " must be 7 digits";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
