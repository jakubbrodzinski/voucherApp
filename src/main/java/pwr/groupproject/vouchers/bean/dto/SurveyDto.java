package pwr.groupproject.vouchers.bean.dto;

import pwr.groupproject.vouchers.bean.model.PossibleAnswers;
import pwr.groupproject.vouchers.bean.model.Question;
import pwr.groupproject.vouchers.bean.model.Survey;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SurveyDto implements Serializable {

    private static final long serialVersionUID = 1822452225511114741L;

    @Pattern(regexp = "[\\pLA-Za-z0-9\\-/ ,.!?:]*")
    @NotBlank
    private String surveyName;
    @Pattern(regexp = "[\\pLA-Za-z0-9\\-/ ,.!?:]*")
    @NotBlank
    private String surveyDescription;
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

    public String getSurveyDescription() {
        return surveyDescription;
    }

    public void setSurveyDescription(String surveyDescription) {
        this.surveyDescription = surveyDescription;
    }

    public Collection<QuestionDto> getQuestions() {
        return questions;
    }

    public void setQuestions(Collection<QuestionDto> questions) {
        this.questions = questions;
    }
}
