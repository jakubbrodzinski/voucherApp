package pwr.groupproject.vouchers.bean.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class User {
    @Column(length = 50)
    private String firstName;
    @Column(length = 50)
    private String lastName;
    private int age;
    @Column(length = 50)
    private String eMail;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }
}
