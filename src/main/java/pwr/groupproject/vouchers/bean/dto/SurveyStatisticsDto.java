package pwr.groupproject.vouchers.bean.dto;

import java.util.ArrayList;
import java.util.List;

public class SurveyStatisticsDto {
    private String surveyName;
    private final List<QuestionStatisticsDto> questionWithAnswersList=new ArrayList<>();
    private String[] country=new String[3];
    private int ammount=0;
    private double age=0;

    public String getSurveyName() {
        return surveyName;
    }

    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }

    public String[] getCountry() {
        return country;
    }

    public void setCountry(String[] country) {
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

    public int getAmmount() {
        return ammount;
    }

    public void setAmmount(int ammount) {
        this.ammount = ammount;
    }
}
