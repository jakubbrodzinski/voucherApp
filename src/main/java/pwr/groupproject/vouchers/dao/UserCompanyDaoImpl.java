package pwr.groupproject.vouchers.dao;

import org.springframework.stereotype.Component;
import pwr.groupproject.vouchers.bean.model.Company;
import pwr.groupproject.vouchers.bean.model.security.UserCompany;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Component
public class UserCompanyDaoImpl implements UserCompanyDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public UserCompany getUserByUserName(String userName) {
        try {
            return entityManager.createQuery("FROM "+UserCompany.class.getName()+" uc WHERE uc.userName='"+userName+"'",UserCompany.class).getSingleResult();
        }catch(NoResultException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void addUserCompany(UserCompany userCompany) {
        entityManager.persist(userCompany);
    }

    @Override
    public void editUser(UserCompany userCompany) {
        entityManager.merge(userCompany);
    }

    @Override
    public UserCompany getUserCompany(int userId) {
        return entityManager.find(UserCompany.class,userId);
    }

    @Override
    public boolean ifEmailIsUsed(String eMail) {
        TypedQuery<Long> query= entityManager.createQuery("SELECT count(*) FROM "+ UserCompany.class.getName()+" uc WHERE uc.userName='"+eMail+"'",Long.class);
        return query.getSingleResult() != 0;
    }

    @Override
    public boolean ifCompanyNameIsUsed(String companyName) {
        TypedQuery<Long> query= entityManager.createQuery("SELECT count(*) FROM "+ Company.class.getName()+" c WHERE c.companyName='"+companyName+"'",Long.class);
        return query.getSingleResult() != 0;
    }

    @Override
    public void deleteUserCompany(UserCompany userCompany) {
        entityManager.remove(userCompany);
    }

    @Override
    public UserCompany getUserCompanyByCompanyId(int companyId) {
        TypedQuery<UserCompany> query=entityManager.createQuery("FROM "+UserCompany.class.getName()+" uc WHERE uc.company='"+companyId+"'",UserCompany.class);
        return query.getSingleResult();
    }

}
