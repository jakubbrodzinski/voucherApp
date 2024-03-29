package pwr.groupproject.vouchers.bean.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "COMPANY")
public class Company implements Serializable {
    private static final long serialVersionUID = -8588675464469442741L;
    @Id
    @GeneratedValue
    @Column(name = "companyId")
    private int Id;
    @Embedded
    private Address companyAddress;
    @Column(length = 50)
    private String companyName;
    @OneToMany(cascade = {CascadeType.REMOVE,CascadeType.MERGE,CascadeType.DETACH},orphanRemoval = true, mappedBy = "company")
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
