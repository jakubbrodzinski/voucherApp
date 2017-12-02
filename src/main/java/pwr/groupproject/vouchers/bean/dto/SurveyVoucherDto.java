package pwr.groupproject.vouchers.bean.dto;

import pwr.groupproject.vouchers.bean.model.Survey;
import pwr.groupproject.vouchers.bean.model.Voucher;
import pwr.groupproject.vouchers.bean.model.enums.DiscountType;

import java.io.Serializable;

public class SurveyVoucherDto implements Serializable {
    private static final long serialVersionUID = 5078475576995835046L;
    private int Id;
    private String surveyName;
    private DiscountType discountType;
    private int discountAmmount;
    private String voucherDescription;

    public SurveyVoucherDto() {
    }

    public SurveyVoucherDto(Survey survey) {
        this.Id = survey.getId();
        this.surveyName = survey.getSurveyName();
        Voucher voucher = survey.getVoucher();
        this.discountAmmount = voucher.getDiscountAmount();
        this.discountType = voucher.getDiscountType();
        this.voucherDescription = voucher.getVoucherDescription();
    }

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

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    public int getDiscountAmmount() {
        return discountAmmount;
    }

    public void setDiscountAmmount(int discountAmmount) {
        this.discountAmmount = discountAmmount;
    }

    public String getVoucherDescription() {
        return voucherDescription;
    }

    public void setVoucherDescription(String voucherDescription) {
        this.voucherDescription = voucherDescription;
    }
}
