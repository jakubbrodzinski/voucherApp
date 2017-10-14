package pwr.groupproject.vouchers.dao;

import org.springframework.stereotype.Component;
import pwr.groupproject.vouchers.bean.model.Voucher;
import pwr.groupproject.vouchers.bean.model.security.UserCompany;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Component
public class UserDaoImpl implements UserDao{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public UserCompany getUserByUserName(String userName) {
        TypedQuery<UserCompany> query= entityManager.createQuery("FROM "+UserCompany.class.getName()+" WHERE userName='"+userName+"'",UserCompany.class);
        return query.getSingleResult();
    }
}
