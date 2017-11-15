package pwr.groupproject.vouchers.bean.dto;

import pwr.groupproject.vouchers.bean.model.enums.QuestionType;

import java.io.Serializable;

public class QuestionDto implements Serializable {
    private static final long serialVersionUID = 1789775665002232105L;

    private String questionBody;
    private QuestionType questionType;

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
