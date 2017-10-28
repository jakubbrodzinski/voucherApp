package pwr.groupproject.vouchers.bean.form.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CustomPasswordValidator implements ConstraintValidator<PasswordValidationConstraint, String> {
   public void initialize(PasswordValidationConstraint constraint) {
   }

   public boolean isValid(String obj, ConstraintValidatorContext context) {
      return true;
   }
}
