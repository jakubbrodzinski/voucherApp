package pwr.groupproject.vouchers.bean.model;

import pwr.groupproject.vouchers.bean.model.enums.DiscountType;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "VOUCHER")
public class Voucher {
    @Id
    @GeneratedValue
    private int Id;
    @OneToOne
    @JoinColumn(name = "surveyId")
    private Survey survey;
    private Date startDate;
    private Date endDate;
    @OneToMany(mappedBy = "voucher",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<VoucherCode> codes = new HashSet<>();
    @Enumerated
    private DiscountType discountType;
    private int discountAmount;
    @Column(name = "details")
    private String voucherDescription;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(int discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getVoucherDescription() {
        return voucherDescription;
    }

    public void setVoucherDescription(String voucherDescription) {
        this.voucherDescription = voucherDescription;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public Set<VoucherCode> getCodes() {
        return codes;
    }

    public void setCodes(Set<VoucherCode> codes) {
        this.codes = codes;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
