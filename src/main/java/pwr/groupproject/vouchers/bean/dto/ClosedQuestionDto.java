package pwr.groupproject.vouchers.bean.dto;

import pwr.groupproject.vouchers.bean.model.PossibleAnswers;

import java.io.Serializable;

public class ClosedQuestionDto extends QuestionDto implements Serializable {
    private static final long serialVersionUID = 1698150456106514895L;

    private PossibleAnswers possibleAnswers = new PossibleAnswers();

    public PossibleAnswers getPossibleAnswers() {
        return possibleAnswers;
    }

    public void setPossibleAnswers(PossibleAnswers possibleAnswers) {
        this.possibleAnswers = possibleAnswers;
    }
}
