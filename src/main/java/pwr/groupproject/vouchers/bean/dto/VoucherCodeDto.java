package pwr.groupproject.vouchers.bean.dto;

import pwr.groupproject.vouchers.bean.model.VoucherCode;
import pwr.groupproject.vouchers.bean.model.enums.DiscountType;

import java.io.Serializable;
import java.util.Date;

public class VoucherCodeDto implements Serializable {

    private static final long serialVersionUID = -8700549926013692302L;
    private String voucherCode;
    private Date startDate;
    private Date endDate;
    private DiscountType discountType;
    private int discountAmount;

    public VoucherCodeDto(){

    }

    public VoucherCodeDto(VoucherCode voucherCode) {
        this.voucherCode = voucherCode.getVoucherCode();
        this.startDate = voucherCode.getVoucher().getStartDate();
        this.endDate = voucherCode.getVoucher().getEndDate();
        this.discountType = voucherCode.getVoucher().getDiscountType();
        this.discountAmount = voucherCode.getVoucher().getDiscountAmount();
    }

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

    public int getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(int discountAmount) {
        this.discountAmount = discountAmount;
    }
}
