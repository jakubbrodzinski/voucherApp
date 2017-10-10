package pwr.groupproject.vouchers.dao;

import org.springframework.stereotype.Component;
import pwr.groupproject.vouchers.bean.model.Company;
import pwr.groupproject.vouchers.bean.model.Voucher;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Collection;

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
}
