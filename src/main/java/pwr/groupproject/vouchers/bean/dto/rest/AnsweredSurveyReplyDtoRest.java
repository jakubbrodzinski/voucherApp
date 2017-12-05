package pwr.groupproject.vouchers.bean.dto.rest;

import pwr.groupproject.vouchers.bean.model.enums.DiscountType;

import java.util.Date;
import java.util.TreeMap;

public class AnsweredSurveyReplyDtoRest {
    private String voucherCode=null;
    private Date startDate=null;
    private Date endDate=null;
    private DiscountType discountType=null;
    private Integer discountAmount=null;
    private String voucherDescription=null;
    private TreeMap<Integer, String> errors = new TreeMap<>();

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
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

    public Integer getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Integer discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getVoucherDescription() {
        return voucherDescription;
    }

    public void setVoucherDescription(String voucherDescription) {
        this.voucherDescription = voucherDescription;
    }

    public TreeMap<Integer, String> getErrors() {
        return errors;
    }

    public void setErrors(TreeMap<Integer, String> errors) {
        this.errors = errors;
    }
}
