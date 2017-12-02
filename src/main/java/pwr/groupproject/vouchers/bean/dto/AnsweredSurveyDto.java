package pwr.groupproject.vouchers.bean.dto;

import pwr.groupproject.vouchers.bean.model.User;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

public class AnsweredSurveyDto implements Serializable {

    private static final long serialVersionUID = -2130602009586486370L;

    private User user;
    private Collection<AnswerDto> answersList;
    private Date date;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Collection<AnswerDto> getAnswersList() {
        return answersList;
    }

    public void setAnswersList(Collection<AnswerDto> answersList) {
        this.answersList = answersList;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
