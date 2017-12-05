package pwr.groupproject.vouchers.bean.dto;

import pwr.groupproject.vouchers.bean.model.enums.QuestionType;

public class QuestionStatisticsDto {
    private String questionBody;
    private QuestionType questionType;
    private AnswerStatisticsDto[] answers=new AnswerStatisticsDto[4];

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

    public AnswerStatisticsDto[] getAnswers() {
        return answers;
    }

    public void setAnswers(AnswerStatisticsDto[] answers) {
        this.answers = answers;
    }
}
