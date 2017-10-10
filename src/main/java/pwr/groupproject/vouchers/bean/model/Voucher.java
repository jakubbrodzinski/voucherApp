package pwr.groupproject.vouchers.bean.model;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "VOUCHER")
public class Voucher {
    @Id
    @GeneratedValue
    private int Id;
    private Date startDate;
    private Date endDate;
    private String code;
    @ManyToOne
    @JoinColumn(name="companyId")
    private Company company;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
