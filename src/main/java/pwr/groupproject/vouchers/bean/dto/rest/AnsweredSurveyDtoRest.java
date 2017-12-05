package pwr.groupproject.vouchers.bean.dto.rest;

import java.util.TreeMap;

public class AnsweredSurveyDtoRest {
    private String email;
    private String country;
    private Integer age;
    private final TreeMap<Integer, String> answersMap = new TreeMap<>();

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public TreeMap<Integer, String> getAnswersMap() {
        return answersMap;
    }
}
