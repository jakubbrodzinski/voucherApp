package pwr.groupproject.vouchers.bean.dto;

import pwr.groupproject.vouchers.bean.model.Question;

import java.io.Serializable;

public class AnswerDto implements Serializable {
    private static final long serialVersionUID = 2272322895956201859L;

    private Question question;
    private String answerBody;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getAnswerBody() {
        return answerBody;
    }

    public void setAnswerBody(String answerBody) {
        this.answerBody = answerBody;
    }
}
