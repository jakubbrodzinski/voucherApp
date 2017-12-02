package pwr.groupproject.vouchers.dao;

import pwr.groupproject.vouchers.bean.model.Company;
import pwr.groupproject.vouchers.bean.model.Voucher;
import pwr.groupproject.vouchers.bean.model.VoucherCode;
import pwr.groupproject.vouchers.bean.model.VoucherCodeDate;

import java.util.Collection;

public interface VoucherDao {
    Collection<Voucher> getAllVouchers();

    Collection<Voucher> getAllVouchersByCompany(Company company);

    Voucher getVoucherById(int Id);

    void addVoucher(Voucher voucher);

    Voucher updateVoucher(Voucher voucher);

    void deleteVoucher(Voucher voucher);

    VoucherCode getVoucherCode(int voucherCodeId);

    VoucherCode updateVoucherCode(VoucherCode voucherCode);

    void deleteVoucherCode(VoucherCode voucherCode);

    void addVoucherCodeDate(VoucherCodeDate voucherCodeDate);

    VoucherCodeDate getVoucherCodeDateById(int voucherCodeDateId);

    void deleteVoucherCodeDate(VoucherCodeDate voucherCodeDate);

    void unBlockAllBlockedVouchersForLongerThan(int hours, int minutes);

}