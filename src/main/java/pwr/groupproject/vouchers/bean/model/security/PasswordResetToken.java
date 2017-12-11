package pwr.groupproject.vouchers.bean.model.security;

import javax.persistence.*;

@Entity
@Table(name="PASSWORDRESET_TOKEN")
public class PasswordResetToken {
    @Id
    @GeneratedValue
    private int id;

    @Column(unique = true)
    private String token;

    @OneToOne(fetch = FetchType.EAGER,optional = true)
    @JoinColumn(name="userId")
    private UserCompany userCompany;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserCompany getUserCompany() {
        return userCompany;
    }

    public void setUserCompany(UserCompany userCompany) {
        this.userCompany = userCompany;
    }
}
