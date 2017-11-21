package pwr.groupproject.vouchers.bean.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "SURVEY")
public class Survey {
    @Id
    @GeneratedValue
    private int Id;
    @Column(length = 50)
    private String surveyName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "survey")
    private Collection<Question> questions = new ArrayList<>();
    @OneToOne(mappedBy = "survey", cascade = {CascadeType.REMOVE,CascadeType.MERGE,CascadeType.DETACH},orphanRemoval = true, optional = true,fetch = FetchType.EAGER)
    private Voucher voucher;
    @OneToMany(mappedBy = "survey", cascade = {CascadeType.REMOVE,CascadeType.MERGE,CascadeType.DETACH},orphanRemoval = true)
    private Collection<AnsweredSurvey> answeredSurveys = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "companyId")
    private Company company;
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getSurveyName() {
        return surveyName;
    }

    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }

    public Collection<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Collection<Question> questions) {
        this.questions = questions;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    public Collection<AnsweredSurvey> getAnsweredSurveys() {
        return answeredSurveys;
    }

    public void setAnsweredSurveys(Collection<AnsweredSurvey> answeredSurveys) {
        this.answeredSurveys = answeredSurveys;
    }

    public boolean isActive() {
        if (voucher == null)
            return false;
        return voucher.getCodes().stream().filter(voucherCode -> voucherCode.getAmmountOfUses() > 0).count() > 0;
    }
}
