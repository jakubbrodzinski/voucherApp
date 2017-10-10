package pwr.groupproject.vouchers.bean.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "ANSWEREDSURVEYS")
public class AnsweredSurvey {
    @javax.persistence.Id
    @GeneratedValue
    private int Id;
    @ManyToOne
    @JoinColumn(name="surveyId")
    private Survey survey;
    @Embedded
    private User user;
    @OneToMany(mappedBy = "answeredSurvey",cascade = CascadeType.ALL)
    private Collection<Answer> answersList=new ArrayList<>();
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public Collection<Answer> getAnswersList() {
        return answersList;
    }

    public void setAnswersList(Collection<Answer> answersList) {
        this.answersList = answersList;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
