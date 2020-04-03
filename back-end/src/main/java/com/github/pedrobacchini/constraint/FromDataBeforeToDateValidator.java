package com.github.pedrobacchini.constraint;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class FromDataBeforeToDateValidator implements ConstraintValidator<FromDataBeforeToDate, Object> {

   private String fromDate;
   private String toDate;

   public void initialize(final FromDataBeforeToDate constraintAnnotation) {
      fromDate = constraintAnnotation.fromDate();
      toDate = constraintAnnotation.toDate();
   }

   public boolean isValid(Object object, ConstraintValidatorContext context) {

      BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(object);
      final LocalDate fromDateObject = (LocalDate) beanWrapper.getPropertyValue(fromDate);
      final LocalDate toDateObj = (LocalDate) beanWrapper.getPropertyValue(toDate);

      boolean isValid = fromDateObject != null && toDateObj != null && fromDateObject.isBefore(toDateObj);

      if (!isValid) {
         context.disableDefaultConstraintViolation();
         context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                 .addPropertyNode(toDate)
                 .addConstraintViolation();
      }

      return isValid;
   }
}
