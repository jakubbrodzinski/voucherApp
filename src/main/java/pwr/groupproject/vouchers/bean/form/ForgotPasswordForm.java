package pwr.groupproject.vouchers.bean.form;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class ForgotPasswordForm {
    @Email
    @NotBlank
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
