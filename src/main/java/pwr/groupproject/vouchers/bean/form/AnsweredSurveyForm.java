package pwr.groupproject.vouchers.bean.form;

import pwr.groupproject.vouchers.bean.dto.AnswerDto;
import pwr.groupproject.vouchers.bean.model.AnsweredSurvey;
import pwr.groupproject.vouchers.bean.model.Question;
import pwr.groupproject.vouchers.bean.model.Survey;
import pwr.groupproject.vouchers.bean.model.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class AnsweredSurveyForm implements Serializable {

    private static final long serialVersionUID = -2959738256673744710L;
    private AnswerDto[] answers;
    @Email
    private String email;
    @Pattern(regexp = "[a-zA-Z]*")
    private String country;
    @Min(1)
    private int age;


    public AnsweredSurveyForm() {
    }

    public AnsweredSurveyForm(int qSize) {
        answers = new AnswerDto[qSize];
        Arrays.setAll(answers, a -> new AnswerDto(""));
    }

    public AnswerDto[] getAnswers() {
        return answers;
    }

    public void setAnswers(AnswerDto[] answers) {
        this.answers = answers;
    }

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
