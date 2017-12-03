package pwr.groupproject.vouchers.bean.form;

import pwr.groupproject.vouchers.bean.dto.AnswerDto;

import java.io.Serializable;

public class AnsweredSurveyForm implements Serializable {

    private static final long serialVersionUID = -2959738256673744710L;
    private AnswerDto[] answers;

    public AnswerDto[] getAnswers() {
        return answers;
    }

    public void setAnswers(AnswerDto[] answers) {
        this.answers = answers;
    }
}
