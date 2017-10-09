package pwr.groupproject.vouchers.bean.model;

import javax.persistence.*;

@Entity
public class Answer {
    @Id
    @GeneratedValue
    private int Id;
    @OneToOne
    private AnsweredSurvey answeredSurvey;
    @ManyToOne
    private Question question;

    private String answer;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public AnsweredSurvey getAnsweredSurvey() {
        return answeredSurvey;
    }

    public void setAnsweredSurvey(AnsweredSurvey answeredSurvey) {
        this.answeredSurvey = answeredSurvey;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
