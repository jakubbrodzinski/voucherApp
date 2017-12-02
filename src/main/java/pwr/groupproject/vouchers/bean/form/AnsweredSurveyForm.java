package pwr.groupproject.vouchers.bean.form;

import pwr.groupproject.vouchers.bean.dto.AnswerDto;

public class AnsweredSurveyForm {

    private AnswerDto[] answer;

    public AnswerDto[] getAnswer() {
        return answer;
    }

    public void setAnswer(AnswerDto[] answer) {
        this.answer = answer;
    }
}
