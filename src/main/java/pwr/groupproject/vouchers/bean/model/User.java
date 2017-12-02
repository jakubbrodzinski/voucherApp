package pwr.groupproject.vouchers.bean.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class User {

    private int age;

    private String country;


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
