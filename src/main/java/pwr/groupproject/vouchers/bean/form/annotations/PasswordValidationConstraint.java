package pwr.groupproject.vouchers.bean.form.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CustomPasswordValidator.class)
public @interface PasswordValidationConstraint {
    String message() default "{pwr.validation.PasswordValidationConstraint}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String filedOne() default "null";
    String filedTwo() default "null";
}
