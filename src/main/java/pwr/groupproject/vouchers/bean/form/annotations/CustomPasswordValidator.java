package pwr.groupproject.vouchers.bean.form.annotations;

import pwr.groupproject.vouchers.bean.form.NewUserCompanyForm;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CustomPasswordValidator implements ConstraintValidator<PasswordValidationConstraint, NewUserCompanyForm> {
   public void initialize(PasswordValidationConstraint constraint) {
   }

   public boolean isValid(NewUserCompanyForm obj, ConstraintValidatorContext context) {
      return true;
   }
}
