package pwr.groupproject.vouchers.bean.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class SurveyDto implements Serializable {
    private static final long serialVersionUID = 3671011244626651928L;

    private String surveyName;
    private Collection<QuestionDto> questions = new ArrayList<>();

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
