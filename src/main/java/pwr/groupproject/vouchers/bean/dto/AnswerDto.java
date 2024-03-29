package pwr.groupproject.vouchers.bean.dto;

import pwr.groupproject.vouchers.bean.form.AnsweredSurveyForm;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class AnswerDto implements Serializable {
    private static final long serialVersionUID = 2272322895956201859L;

    @NotEmpty(groups = AnsweredSurveyForm.ValidationGroup1.class)
    private String answerBody;

    public AnswerDto(String answerBody) {
        this.answerBody = answerBody;
    }

    public AnswerDto() {
    }

    public String getAnswerBody() {
        return answerBody;
    }

    public void setAnswerBody(String answerBody) {
        this.answerBody = answerBody;
    }
}
