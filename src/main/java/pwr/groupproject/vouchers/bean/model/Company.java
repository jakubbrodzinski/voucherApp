package pwr.groupproject.vouchers.bean.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Company {

    @javax.persistence.Id
    @GeneratedValue
    private int Id;
    @Embedded
    private Address companyAddress;
    private String companyName;
    @OneToMany
    private Collection<Survey> companysSurveys=new ArrayList<>();
    @OneToMany
    private Collection<AnsweredSurvey> answeredSurveys=new ArrayList<>();
    @OneToMany
    private Set<Voucher> voucherSet=new HashSet<>();

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Address getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(Address companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Collection<Survey> getCompanysSurveys() {
        return companysSurveys;
    }

    public void setCompanysSurveys(Collection<Survey> companysSurveys) {
        this.companysSurveys = companysSurveys;
    }

    public Collection<AnsweredSurvey> getAnsweredSurveys() {
        return answeredSurveys;
    }

    public void setAnsweredSurveys(Collection<AnsweredSurvey> answeredSurveys) {
        this.answeredSurveys = answeredSurveys;
    }
}
