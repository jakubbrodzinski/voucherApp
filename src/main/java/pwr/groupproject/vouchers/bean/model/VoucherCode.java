package pwr.groupproject.vouchers.bean.model;

import javax.persistence.*;

@Entity
@Table(name = "VOUCHER_CODE")
public class VoucherCode {
    @GeneratedValue
    @Id
    private int Id;
    @Column(name = "code")
    private String voucherCode;
    @ManyToOne
    @JoinColumn(name = "voucherId")
    private Voucher voucher;
    private boolean isAvaible=true;

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    public boolean isAvaible() {
        return isAvaible;
    }

    public void setAvaible(boolean avaible) {
        isAvaible = avaible;
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
