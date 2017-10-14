package pwr.groupproject.vouchers.bean.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class VoucherCode {
    @Column(name = "code")
    private String voucherCode;

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VoucherCode that = (VoucherCode) o;

        return voucherCode != null ? voucherCode.equals(that.voucherCode) : that.voucherCode == null;
    }

    @Override
    public int hashCode() {
        return voucherCode != null ? voucherCode.hashCode() : 0;
    }
}
