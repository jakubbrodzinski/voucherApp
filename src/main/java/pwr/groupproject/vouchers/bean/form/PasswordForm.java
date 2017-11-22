package pwr.groupproject.vouchers.bean.form;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import pwr.groupproject.vouchers.bean.form.annotations.PasswordValidationConstraint;

@PasswordValidationConstraint(filedOne = "password",filedTwo = "repeatedPassword")
public class PasswordForm {
    private String oldPassword;
    @Length(min=6)
    private String password;
    @NotBlank
    private String repeatedPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

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
}
