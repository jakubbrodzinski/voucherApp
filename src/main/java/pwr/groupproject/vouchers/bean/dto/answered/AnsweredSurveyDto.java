package pwr.groupproject.vouchers.bean.dto.answered;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

public class AnsweredSurveyDto implements Serializable {

    private static final long serialVersionUID = -2130602009586486370L;

    private int Id;
    private int age;
    private String country;
    private Collection<AnsweredQuestionDto> answersList;
    private Date date;

    public AnsweredSurveyDto() {
    }

    public AnsweredSurveyDto(int Id,int age, String country, Date date) {
        this.Id=Id;
        this.age = age;
        this.country = country;
        this.date = date;
    }

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

    public Collection<AnsweredQuestionDto> getAnswersList() {
        return answersList;
    }

    public void setAnswersList(Collection<AnsweredQuestionDto> answersList) {
        this.answersList = answersList;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
