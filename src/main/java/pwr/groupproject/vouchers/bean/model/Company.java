package pwr.groupproject.vouchers.bean.model;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "COMPANY")
public class Company {
    @javax.persistence.Id
    @GeneratedValue
    @Column(name = "companyId")
    private int Id;
    @Embedded
    private Address companyAddress;
    @Column(length = 50)
    private String companyName;
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "company")
    private Collection<Survey> companysSurveys = new ArrayList<>();

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
}
