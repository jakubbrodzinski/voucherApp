package pwr.groupproject.vouchers.bean.form;

import pwr.groupproject.vouchers.bean.dto.AnswerDto;

public class AnsweredSurveyForm {

    private AnswerDto[] answers;

    public AnswerDto[] getAnswers() {
        return answers;
    }

    public void setAnswers(AnswerDto[] answers) {
        this.answers = answers;
    }
}
