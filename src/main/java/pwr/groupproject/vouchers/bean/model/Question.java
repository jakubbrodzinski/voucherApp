package pwr.groupproject.vouchers.bean.model;

import pwr.groupproject.vouchers.bean.model.enums.QuestionType;

import javax.persistence.*;

@Entity
@SecondaryTable(name = "QUESTION_POSSIBLEANSWER", pkJoinColumns = @PrimaryKeyJoinColumn(name = "QuestionId"))
@Table(name = "QUESTIONS")
public class Question {
    @Id
    @GeneratedValue
    private int Id;
    @Column(length = 400)
    private String questionBody;
    @Enumerated(value = EnumType.ORDINAL)
    private QuestionType questionType;
    @ManyToOne
    @JoinColumn(name = "surveyId")
    private Survey survey;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "possibleAnswerA", column = @Column(table = "QUESTION_POSSIBLEANSWER")),
            @AttributeOverride(name = "possibleAnswerB", column = @Column(table = "QUESTION_POSSIBLEANSWER")),
            @AttributeOverride(name = "possibleAnswerC", column = @Column(table = "QUESTION_POSSIBLEANSWER")),
            @AttributeOverride(name = "possibleAnswerD", column = @Column(table = "QUESTION_POSSIBLEANSWER"))
    })
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
