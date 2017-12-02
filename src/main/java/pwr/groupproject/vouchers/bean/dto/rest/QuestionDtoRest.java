package pwr.groupproject.vouchers.bean.dto.rest;

import pwr.groupproject.vouchers.bean.model.PossibleAnswers;
import pwr.groupproject.vouchers.bean.model.Question;
import pwr.groupproject.vouchers.bean.model.enums.QuestionType;

import java.io.Serializable;

public class QuestionDtoRest implements Serializable {
    private static final long serialVersionUID = 1789775665002232105L;
    private int Id;
    private String questionBody;
    private QuestionType questionType;
    private PossibleAnswers possibleAnswers = null;

    public QuestionDtoRest() {
    }

    public QuestionDtoRest(Question question) {
        this.Id = question.getId();
        this.questionBody = question.getQuestionBody();
        this.questionType = question.getQuestionType();
        if (question.getPossibleAnswers() != null) {
            possibleAnswers = new PossibleAnswers();
            PossibleAnswers p = question.getPossibleAnswers();
            possibleAnswers.setPossibleAnswerA(p.getPossibleAnswerA());
            possibleAnswers.setPossibleAnswerB(p.getPossibleAnswerB());
            possibleAnswers.setPossibleAnswerC(p.getPossibleAnswerC());
            possibleAnswers.setPossibleAnswerD(p.getPossibleAnswerD());
        }
    }

    public PossibleAnswers getPossibleAnswers() {
        return possibleAnswers;
    }

    public void setPossibleAnswers(PossibleAnswers possibleAnswers) {
        this.possibleAnswers = possibleAnswers;
    }

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
}
