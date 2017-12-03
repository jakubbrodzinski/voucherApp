package pwr.groupproject.vouchers.bean.form;

import pwr.groupproject.vouchers.bean.model.User;

import java.io.Serializable;

public class UserForm implements Serializable{
    private static final long serialVersionUID = -5571715335830804242L;

    private String email;
    private User user;

    public UserForm() {
        this.user = new User();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
