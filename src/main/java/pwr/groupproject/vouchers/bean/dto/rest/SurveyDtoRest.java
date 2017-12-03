package pwr.groupproject.vouchers.bean.dto.rest;

import pwr.groupproject.vouchers.bean.model.Question;
import pwr.groupproject.vouchers.bean.model.Survey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class SurveyDtoRest implements Serializable {
    private static final long serialVersionUID = 3671011244626651928L;
    private int Id;
    private String surveyName;
    private final Collection<QuestionDtoRest> questionsDto = new ArrayList<>();

    public SurveyDtoRest() {
    }

    public SurveyDtoRest(Survey survey) {
        this.Id = survey.getId();
        this.surveyName = survey.getSurveyName();
        Collection<Question> questions = survey.getQuestions();
        questions.stream().map(QuestionDtoRest::new).forEach(questionsDto::add);
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getSurveyName() {
        return surveyName;
    }

    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }

    public Collection<QuestionDtoRest> getQuestionsDto() {
        return questionsDto;
    }
}
