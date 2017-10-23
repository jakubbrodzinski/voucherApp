package pwr.groupproject.vouchers.bean.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="VOCUHERCODE_DATE")
public class VoucherCodeDate {
    @Id
    @GeneratedValue
    private int Id;
    @ManyToOne
    @JoinColumn(name = "codeId")
    private VoucherCode voucherCode;
    @Temporal(TemporalType.TIMESTAMP)
    private Date useDate;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public VoucherCode getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(VoucherCode voucherCode) {
        this.voucherCode = voucherCode;
    }

    public Date getUseDate() {
        return useDate;
    }

    public void setUseDate(Date useDate) {
        this.useDate = useDate;
    }
}
