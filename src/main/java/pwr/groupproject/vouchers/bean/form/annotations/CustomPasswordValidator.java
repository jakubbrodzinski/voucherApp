package pwr.groupproject.vouchers.bean.form.annotations;

import pwr.groupproject.vouchers.bean.form.NewUserCompanyForm;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

public class CustomPasswordValidator implements ConstraintValidator<PasswordValidationConstraint, Object> {
    private String firstField;
    private String secondField;

   public void initialize(PasswordValidationConstraint constraint) {
       firstField=constraint.filedOne();
       secondField=constraint.filedTwo();
   }

   public boolean isValid(Object obj, ConstraintValidatorContext context) {
       Class<? extends Object> aClass=obj.getClass();
       try {
           Field first = aClass.getDeclaredField(firstField);
           first.setAccessible(true);
           Field second = aClass.getDeclaredField(secondField);
           second.setAccessible(true);
           if(first.getType() != second.getType()){
               return false;
           }
           Object valueFirst=first.get(obj);
           Object valueSecond=second.get(obj);
           if(valueFirst==null || valueSecond==null )
               return false;
           if(valueFirst.equals(valueSecond))
               return true;
       }catch (NoSuchFieldException e){
           e.printStackTrace();
           return false;
       }catch(IllegalAccessException ex) {
           ex.printStackTrace();
           return false;
       }
       return false;
   }
}
