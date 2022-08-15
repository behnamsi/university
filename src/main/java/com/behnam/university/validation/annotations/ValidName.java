package com.behnam.university.validation.annotations;


import com.behnam.university.validation.validators.NameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {NameValidator.class})
public @interface ValidName {
    String message() default "name must contain only letters and size between 3-20";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
