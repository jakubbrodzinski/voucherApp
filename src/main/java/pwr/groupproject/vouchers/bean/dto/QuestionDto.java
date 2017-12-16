package pwr.groupproject.vouchers.bean.dto;

import pwr.groupproject.vouchers.bean.model.PossibleAnswers;
import pwr.groupproject.vouchers.bean.model.enums.QuestionType;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class QuestionDto implements Serializable {
    private static final long serialVersionUID = 1789775665002232105L;

    @Pattern(regexp = "[\\pLA-Za-z0-9\\-/ ,.!?:]*")
    @NotBlank
    private String questionBody;
    private QuestionType questionType;
    @Valid
    private PossibleAnswers possibleAnswers = new PossibleAnswers();

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

    public PossibleAnswers getPossibleAnswers() {
        return possibleAnswers;
    }

    public void setPossibleAnswers(PossibleAnswers possibleAnswers) {
        this.possibleAnswers = possibleAnswers;
    }
}
