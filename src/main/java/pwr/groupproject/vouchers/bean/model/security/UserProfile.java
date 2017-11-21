package pwr.groupproject.vouchers.bean.model.security;

import org.springframework.security.core.GrantedAuthority;
import pwr.groupproject.vouchers.bean.enums.security.UserProfileType;

import javax.persistence.*;

@Entity
@Table(name = "USER_PROFILE")
public class UserProfile implements GrantedAuthority {
    @javax.persistence.Id
    @GeneratedValue
    private int Id;
    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private UserProfileType type=UserProfileType.COMPANY;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public UserProfileType getType() {
        return type;
    }

    public void setType(UserProfileType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserProfile that = (UserProfile) o;

        if (Id != that.Id) return false;
        return type == that.type;
    }

    @Override
    public int hashCode() {
        int result = Id;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String getAuthority() {
        return type.toString() ;
    }
}
