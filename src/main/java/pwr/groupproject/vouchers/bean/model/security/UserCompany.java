package pwr.groupproject.vouchers.bean.model.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pwr.groupproject.vouchers.bean.model.Company;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "USER_COMPANY")
public class UserCompany{
    @Id
    @GeneratedValue
    private int Id;
    @Column(length = 50)
    private String userName;
    private String encodedPassword;
    @Column(length = 50)
    private String eMail;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "companyId")
    private Company company;
    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "APP_USER_USER_PROFILE",
            joinColumns = { @JoinColumn(name = "USER_ID") },
            inverseJoinColumns = { @JoinColumn(name = "USER_PROFILE_ID") })
    private final Set<UserProfile> userProfiles = new HashSet<>(Arrays.asList(new UserProfile()));
    private boolean isActivated=false;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return encodedPassword;
    }

    public void setPassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Set<UserProfile> getUserProfiles() {
        return userProfiles;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public void setActivated(boolean activated) {
        isActivated = activated;
    }

    @Override
    public String toString() {
        return "UserCompany{" +
                "Id=" + Id +
                ", userName='" + userName + '\'' +
                ", encodedPassword='" + encodedPassword + '\'' +
                ", eMail='" + eMail + '\'' +
                ", company=" + company +
                ", userProfiles=" + userProfiles +
                ", isActivated=" + isActivated +
                '}';
    }
}
