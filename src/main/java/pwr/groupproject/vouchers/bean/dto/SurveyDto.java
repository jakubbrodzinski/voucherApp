package pwr.groupproject.vouchers.bean.dto;

import java.util.Collection;

public class SurveyDto {

    private String surveyName;
    private Collection<QuestionDto> questions;

    public String getSurveyName() {
        return surveyName;
    }

    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }

    public Collection<QuestionDto> getQuestions() {
        return questions;
    }

    public void setQuestions(Collection<QuestionDto> questions) {
        this.questions = questions;
    }
}
