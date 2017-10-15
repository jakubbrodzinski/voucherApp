package pwr.groupproject.vouchers.dao;

import org.springframework.stereotype.Component;
import pwr.groupproject.vouchers.bean.model.security.UserCompany;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Component
public class UserCompanyDaoImpl implements UserCompanyDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public UserCompany getUserByUserName(String userName) {
        TypedQuery<UserCompany> query= entityManager.createQuery("FROM "+ UserCompany.class.getName()+" WHERE userName='"+userName+"'", UserCompany.class);
        return query.getSingleResult();
    }

    @Override
    public void createNewUser(UserCompany userCompany) {
        entityManager.persist(userCompany);
    }

    @Override
    public void editUser(UserCompany userCompany) {
        entityManager.merge(userCompany);
    }

    @Override
    public UserCompany getUser(int userId) {
        return entityManager.find(UserCompany.class,userId);
    }

    @Override
    public boolean ifEmailIsUsed(String eMail) {
        TypedQuery<Integer> query= entityManager.createQuery("SELCT count(*) FROM "+ UserCompany.class.getName()+" uc WHERE uc.eMail='"+eMail+"'",Integer.class);
        if(query.getSingleResult()!=0)
            return false;
        else
            return true;
    }
}
