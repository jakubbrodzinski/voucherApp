package pwr.groupproject.vouchers.bean.model.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pwr.groupproject.vouchers.bean.model.Company;

import javax.persistence.*;

import java.util.*;

@Entity
@Table(name = "USER_COMPANY")
public class UserCompany implements UserDetails{
    private static final long serialVersionUID = -8998307817499676750L;
    @Id
    @GeneratedValue
    private int Id;
    @Column(length = 50)
    private String userName;
    private String encodedPassword;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "companyId")
    private Company company;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "APP_USER_USER_PROFILE",
            joinColumns = { @JoinColumn(name = "USER_ID") },
            inverseJoinColumns = { @JoinColumn(name = "USER_PROFILE_ID") })
    private final Set<UserProfile> userProfiles = new HashSet<>(Arrays.asList(new UserProfile()));
    private boolean isEnabled=false;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userProfiles;
    }

    @Override
    public String getPassword() {
        return encodedPassword;
    }

    public void setPassword(String password) {
        this.encodedPassword = password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    public void setUsername(String userName) {
        this.userName = userName;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
