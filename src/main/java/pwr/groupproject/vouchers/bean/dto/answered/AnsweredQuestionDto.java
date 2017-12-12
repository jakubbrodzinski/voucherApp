package pwr.groupproject.vouchers.bean.dto.answered;

import pwr.groupproject.vouchers.bean.model.enums.QuestionType;

import java.util.Arrays;

public class AnsweredQuestionDto {
    private String questionBody;
    private QuestionType questionType;
    private AnsweredAnswerDto[] answerDtos = new AnsweredAnswerDto[4];

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

    public AnsweredAnswerDto[] getAnswerDtos() {
        return answerDtos;
    }

    public void setAnswerDtos(AnsweredAnswerDto[] answerDtos) {
        this.answerDtos = answerDtos;
    }

}
