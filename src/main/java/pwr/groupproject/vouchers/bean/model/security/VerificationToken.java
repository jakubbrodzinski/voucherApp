package pwr.groupproject.vouchers.bean.model.security;

import pwr.groupproject.vouchers.bean.model.Company;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Entity
@Table(name="VERIFICATION_TOKEN")
public class VerificationToken {

    @Id
    @GeneratedValue
    private int Id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private UserCompany userCompany;
    @Column(unique = true)
    private String token;
    private Date expirationDate;

    private Date calculateExpDate(){
        LocalDateTime localDateTime=LocalDateTime.now().plusDays(1);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public VerificationToken() {
        expirationDate=calculateExpDate();
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public UserCompany getUserCompany() {
        return userCompany;
    }

    public void setUserCompany(UserCompany userCompany) {
        this.userCompany = userCompany;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
