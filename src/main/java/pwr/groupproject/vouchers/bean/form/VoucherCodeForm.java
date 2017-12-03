package pwr.groupproject.vouchers.bean.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;

public class VoucherCodeForm {
    @Pattern(regexp = "[0-9a-zA-Z -.]*")
    @NotBlank
    private String voucherCode;
    @DecimalMin("0")
    private int amountOfUses;

    public int getAmountOfUses() {
        return amountOfUses;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setAmountOfUses(int amountOfUses) {
        this.amountOfUses = amountOfUses;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }
}
