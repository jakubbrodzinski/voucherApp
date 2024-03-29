package pwr.groupproject.vouchers.bean.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "VOUCHER_CODE")
public class VoucherCode {
    @GeneratedValue
    @Id
    private int Id;
    private String voucherCode;
    @ManyToOne
    @JoinColumn(name = "voucherId")
    private Voucher voucher;
    private int ammountOfUses = 1;
    private int ammountofBlocked = 0;
    @OneToMany(mappedBy = "voucherCode", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Collection<VoucherCodeDate> voucherCodeDates = new ArrayList<>();

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

    public int getAmmountOfUses() {
        return ammountOfUses;
    }

    public void setAmmountOfUses(int ammountOfUses) {
        this.ammountOfUses = ammountOfUses;
    }

    public Collection<VoucherCodeDate> getVoucherCodeDates() {
        return voucherCodeDates;
    }

    public void setVoucherCodeDates(Collection<VoucherCodeDate> voucherCodeDates) {
        this.voucherCodeDates = voucherCodeDates;
    }

    public int getAmmountofBlocked() {
        return ammountofBlocked;
    }

    public void setAmmountofBlocked(int ammountofBlocked) {
        this.ammountofBlocked = ammountofBlocked;
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

    @Override
    public String toString() {
        return "VoucherCode{" +
                "Id=" + Id +
                ", voucherCode='" + voucherCode + '\'' +
                ", voucher=" + voucher +
                ", ammountOfUses=" + ammountOfUses +
                ", voucherCodeDates=" + voucherCodeDates +
                '}';
    }
}
