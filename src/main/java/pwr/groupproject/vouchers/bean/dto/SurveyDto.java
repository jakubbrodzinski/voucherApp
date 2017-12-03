package pwr.groupproject.vouchers.bean.dto;

import pwr.groupproject.vouchers.bean.model.Question;
import pwr.groupproject.vouchers.bean.model.Survey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SurveyDto implements Serializable {
    private static final long serialVersionUID = 3671011244626651928L;

    private String surveyName;
    private Collection<QuestionDto> questions = new ArrayList<>();

    public SurveyDto() {
        super();
    }

    public SurveyDto(Survey survey) {
        this.surveyName = survey.getSurveyName();
        List<QuestionDto> questionDtos = new ArrayList<>(survey.getQuestions().size());
        for(Question question : survey.getQuestions()) {
            QuestionDto questionDto = new QuestionDto();
            questionDto.setQuestionBody(question.getQuestionBody());
            questionDto.setQuestionType(question.getQuestionType());
            questionDtos.add(questionDto);
        }
        this.questions = questionDtos;
    }

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
