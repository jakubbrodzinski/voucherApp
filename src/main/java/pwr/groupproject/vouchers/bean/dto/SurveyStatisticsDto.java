package pwr.groupproject.vouchers.bean.dto;

import java.util.ArrayList;
import java.util.List;

public class SurveyStatisticsDto {
    private String surveyName;
    private final List<QuestionStatisticsDto> questionWithAnswersList=new ArrayList<>();
    private String country;
    private double age=0;

    public String getSurveyName() {
        return surveyName;
    }

    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getAge() {
        return age;
    }

    public void setAge(double age) {
        this.age = age;
    }

    public List<QuestionStatisticsDto> getQuestionWithAnswersList() {
        return questionWithAnswersList;
    }
}
