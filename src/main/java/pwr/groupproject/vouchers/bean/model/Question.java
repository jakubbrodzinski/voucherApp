package pwr.groupproject.vouchers.bean.model;

import pwr.groupproject.vouchers.bean.enums.QuestionType;

import javax.persistence.*;

@Entity
public class Question {
    @Id
    @GeneratedValue
    private int Id;
    private String questionBody;
    @Enumerated(value = EnumType.ORDINAL)
    private QuestionType questionType;
    @ManyToOne
    private Survey survey;
    @Embedded
    //!!!!!
    private PossibleAnswers possibleAnswers;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getQuestionBody() {
        return questionBody;
    }

    public void setQuestionBody(String questionBody) {
        this.questionBody = questionBody;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public PossibleAnswers getPossibleAnswers() {
        return possibleAnswers;
    }

    public void setPossibleAnswers(PossibleAnswers possibleAnswers) {
        this.possibleAnswers = possibleAnswers;
    }
}
