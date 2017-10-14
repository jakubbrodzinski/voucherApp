package pwr.groupproject.vouchers.bean.model;

import pwr.groupproject.vouchers.bean.enums.DiscountType;
import pwr.groupproject.vouchers.bean.enums.VoucherType;

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
    @ManyToOne
    @JoinColumn(name = "surveyId")
    private Survey survey;
    private Date startDate;
    private Date endDate;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "VOUCHER_CODE", joinColumns = @JoinColumn(name = "voucherId"))
    private Set<VoucherCode> codes = new HashSet<>();
    @Enumerated
    private VoucherType voucherType;
    @Column(name = "quant")
    private int voucherQuantity;
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

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(VoucherType voucherType) {
        this.voucherType = voucherType;
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

    public int getVoucherQuantity() {
        return voucherQuantity;
    }

    public void setVoucherQuantity(int voucherQuantity) {
        this.voucherQuantity = voucherQuantity;
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
