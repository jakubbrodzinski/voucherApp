package pwr.groupproject.vouchers.bean.dto;

import pwr.groupproject.vouchers.bean.model.enums.QuestionType;

public class QuestionDto {

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
