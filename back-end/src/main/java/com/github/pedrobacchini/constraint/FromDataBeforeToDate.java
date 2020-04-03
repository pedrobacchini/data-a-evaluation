package com.github.pedrobacchini.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = FromDataBeforeToDateValidator.class)
@Documented
public @interface FromDataBeforeToDate {

    String message() default "{br.com.cilia.constraints.FromDataBeforeToDate.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String fromDate();

    String toDate();
}
