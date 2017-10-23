package pwr.groupproject.vouchers.dao;

import org.springframework.stereotype.Component;
import pwr.groupproject.vouchers.bean.model.Company;
import pwr.groupproject.vouchers.bean.model.Voucher;
import pwr.groupproject.vouchers.bean.model.VoucherCode;
import pwr.groupproject.vouchers.bean.model.VoucherCodeDate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

@Component
public class VoucherDaoImpl implements VoucherDao {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Collection<Voucher> getAllVouchers() {
        return entityManager.createQuery("FROM "+Voucher.class.getName(),Voucher.class).getResultList();
    }

    @Override
    public Collection<Voucher> getAllVouchersByCompany(Company company) {
        return entityManager.createQuery("FROM "+Voucher.class.getName()+" WHERE company='"+company.getId()+"'",Voucher.class).getResultList();
    }

    @Override
    public Voucher getVoucherById(int Id) {
        return entityManager.find(Voucher.class,Id);
    }

    @Override
    public void addVoucher(Voucher voucher) {
        entityManager.persist(voucher);
    }

    @Override
    public void updateVoucher(Voucher voucher) {
        entityManager.merge(voucher);
    }

    @Override
    public void deleteVoucher(Voucher voucher) {
        entityManager.remove(voucher);
    }

    @Override
    public VoucherCode getVoucherCode(int voucherCodeId) {
        return entityManager.find(VoucherCode.class,voucherCodeId);
    }

    @Override
    public void updateVoucherCode(VoucherCode voucherCode) {
        entityManager.merge(voucherCode);
    }

    @Override
    public void deleteVoucherCode(VoucherCode voucherCode) {
        entityManager.remove(voucherCode);
    }

    @Override
    public void addVoucherCodeDate(VoucherCodeDate voucherCodeDate) {
        entityManager.persist(voucherCodeDate);
    }

    @Override
    public void deleteVoucherCodeDateByCodeId(int voucherCodeId) {
        entityManager.createQuery("DELETE FROM "+VoucherCodeDate.class.getName()+ " WHERE VoucherCodeDate .voucherCode='"+voucherCodeId+"'");
    }

    @Override
    public void deleteVoucherCodeDateOlderThan(int hours, int minutes) {
        //TO-DO
    }
}
