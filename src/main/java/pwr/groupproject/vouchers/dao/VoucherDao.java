package pwr.groupproject.vouchers.dao;

import pwr.groupproject.vouchers.bean.model.Company;
import pwr.groupproject.vouchers.bean.model.Voucher;
import pwr.groupproject.vouchers.bean.model.VoucherCode;

import java.util.Collection;

public interface VoucherDao {
    Collection<Voucher> getAllVouchers();
    Collection<Voucher> getAllVouchersByCompany(Company company);

    Voucher getVoucherById(int Id);

    void addVoucher(Voucher voucher);
    void updateVoucher(Voucher voucher);
    void deleteVoucher(Voucher voucher);

    VoucherCode getVoucherCode(int voucherCodeId);
    void updateVoucherCode(VoucherCode voucherCode);
    void deleteVoucherCode(VoucherCode voucherCode);

}