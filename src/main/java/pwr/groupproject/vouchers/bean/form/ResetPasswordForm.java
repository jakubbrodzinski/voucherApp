package pwr.groupproject.vouchers.bean.form;

import org.hibernate.validator.constraints.Length;
import pwr.groupproject.vouchers.bean.form.annotations.PasswordValidationConstraint;

import javax.validation.constraints.NotBlank;

@PasswordValidationConstraint(filedOne = "password", filedTwo = "repeatedPassword")
public class ResetPasswordForm {
    @NotBlank
    @Length(min = 6)
    private String password;
    @NotBlank
    private String repeatedPassword;

    private String resetPasswordToken;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }
}
