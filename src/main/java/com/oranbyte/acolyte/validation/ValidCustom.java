package com.oranbyte.acolyte.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.oranbyte.acolyte.validation.impl.CustomValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = CustomValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCustom {
	String message() default  "Invalid Input!";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}