package pwr.groupproject.vouchers.dao;

import org.springframework.stereotype.Component;
import pwr.groupproject.vouchers.bean.model.Company;
import pwr.groupproject.vouchers.bean.model.Voucher;
import pwr.groupproject.vouchers.bean.model.VoucherCode;
import pwr.groupproject.vouchers.bean.model.VoucherCodeDate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Component
public class VoucherDaoImpl implements VoucherDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Collection<Voucher> getAllVouchers() {
        return entityManager.createQuery("FROM " + Voucher.class.getName(), Voucher.class).getResultList();
    }

    @Override
    public Collection<Voucher> getAllVouchersByCompany(Company company) {
        return entityManager.createQuery("FROM " + Voucher.class.getName() + " WHERE company='" + company.getId() + "'", Voucher.class).getResultList();
    }

    @Override
    public Voucher getVoucherById(int Id) {
        return entityManager.find(Voucher.class, Id);
    }

    @Override
    public void addVoucher(Voucher voucher) {
        entityManager.persist(voucher);
    }

    @Override
    public Voucher updateVoucher(Voucher voucher) {
        return entityManager.merge(voucher);
    }

    @Override
    public void deleteVoucher(Voucher voucher) {
        voucher.getSurvey().setVoucher(null);
        entityManager.remove(voucher);
    }

    @Override
    public VoucherCode getVoucherCode(int voucherCodeId) {
        return entityManager.find(VoucherCode.class, voucherCodeId);
    }

    @Override
    public VoucherCode updateVoucherCode(VoucherCode voucherCode) {
        return entityManager.merge(voucherCode);
    }

    @Override
    public void deleteVoucherCode(VoucherCode voucherCode) {
        voucherCode.getVoucher().getCodes().remove(voucherCode);
        entityManager.remove(voucherCode);
    }

    @Override
    public void addVoucherCodeDate(VoucherCodeDate voucherCodeDate) {
        entityManager.persist(voucherCodeDate);
    }

    @Override
    public VoucherCodeDate getVoucherCodeDateById(int voucherCodeDateId) {
        return entityManager.find(VoucherCodeDate.class, voucherCodeDateId);
    }

    @Override
    public void deleteVoucherCodeDate(VoucherCodeDate voucherCodeDate) {
        entityManager.remove(voucherCodeDate);
    }

    @Override
    public void unBlockAllBlockedVouchersForLongerThan(int hours, int minutes) {
        List<VoucherCodeDate> voucherCodeDates = entityManager.createQuery("FROM " + VoucherCodeDate.class.getName(), VoucherCodeDate.class).getResultList();
        LocalDateTime localDateTime = LocalDateTime.now().minusHours(hours).minusMinutes(minutes);
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        voucherCodeDates.stream().filter(v -> v.getUseDate().compareTo(date) < 0).forEach(entityManager::remove);
    }
}
