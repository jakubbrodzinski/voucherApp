package pwr.groupproject.vouchers.bean.dto;

import pwr.groupproject.vouchers.bean.model.PossibleAnswers;
import pwr.groupproject.vouchers.bean.model.Question;
import pwr.groupproject.vouchers.bean.model.Survey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SurveyDto implements Serializable {

    private static final long serialVersionUID = 1822452225511114741L;

    private String surveyName;
    private Collection<ClosedQuestionDto> questions = new ArrayList<>();

    public SurveyDto() {
        super();
    }

    public SurveyDto(Survey survey) {
        this.surveyName = survey.getSurveyName();
        List<ClosedQuestionDto> questionDtos = new ArrayList<>(survey.getQuestions().size());
        for(Question question : survey.getQuestions()) {
            ClosedQuestionDto questionDto = new ClosedQuestionDto();
            questionDto.setQuestionBody(question.getQuestionBody());
            questionDto.setQuestionType(question.getQuestionType());
            PossibleAnswers possibleAnswers = new PossibleAnswers();
            possibleAnswers.setPossibleAnswerA(question.getPossibleAnswers().getPossibleAnswerA());
            possibleAnswers.setPossibleAnswerB(question.getPossibleAnswers().getPossibleAnswerB());
            possibleAnswers.setPossibleAnswerC(question.getPossibleAnswers().getPossibleAnswerC());
            possibleAnswers.setPossibleAnswerD(question.getPossibleAnswers().getPossibleAnswerD());
            questionDto.setPossibleAnswers(possibleAnswers);
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

    public Collection<ClosedQuestionDto> getQuestions() {
        return questions;
    }

    public void setQuestions(Collection<ClosedQuestionDto> questions) {
        this.questions = questions;
    }
}
