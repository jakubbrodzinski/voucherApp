package pwr.groupproject.vouchers.dao;

import pwr.groupproject.vouchers.bean.model.Company;
import pwr.groupproject.vouchers.bean.model.Voucher;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

public class VoucherDaoImpl implements VoucherDao {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Collection<Voucher> getAllVouchers() {
        return entityManager.createQuery("select vo from Voucher vo",Voucher.class).getResultList();
    }

    @Override
    public Collection<Voucher> getAllVouchersByCompany(Company company) {
        return entityManager.createQuery("FROM Voucher WHERE companyId='"+company.getId()+"'",Voucher.class).getResultList();
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
}
