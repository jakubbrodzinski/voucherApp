package pwr.groupproject.vouchers.bean.dto;

import pwr.groupproject.vouchers.bean.model.PossibleAnswers;
import pwr.groupproject.vouchers.bean.model.enums.QuestionType;

import java.util.stream.IntStream;

public class QuestionStatisticsDto {
    private String questionBody;
    private QuestionType questionType;
    private AnswerStatisticsDto[] answers=new AnswerStatisticsDto[4];

    public QuestionStatisticsDto(){
        IntStream.range(0,answers.length).forEach(u->answers[u]=new AnswerStatisticsDto());
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

    public AnswerStatisticsDto[] getAnswers() {
        return answers;
    }

    public void setAnswers(AnswerStatisticsDto[] answers) {
        this.answers = answers;
    }

    public void setPossibleAnswers(PossibleAnswers possibleAnswers){
        answers[0].setAnswersBody(possibleAnswers.getPossibleAnswerA());
        answers[1].setAnswersBody(possibleAnswers.getPossibleAnswerB());
        answers[2].setAnswersBody(possibleAnswers.getPossibleAnswerC());
        answers[3].setAnswersBody(possibleAnswers.getPossibleAnswerD());
    }
}
