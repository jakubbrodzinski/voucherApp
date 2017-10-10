package pwr.groupproject.vouchers.dao;

import pwr.groupproject.vouchers.bean.model.Company;
import pwr.groupproject.vouchers.bean.model.Voucher;

import java.util.Collection;

public interface VoucherDao {
    Collection<Voucher> getAllActiveVouchers();
    Collection<Voucher> getAllActiveVouchersByCompany(Company company);

    Voucher getVoucherById(int Id);

    void addVoucher(Voucher voucher);
    void deployVoucher(Voucher voucher);
    void updateVoucher(Voucher voucher);
    void deleteVoucher(Voucher voucher);
}
