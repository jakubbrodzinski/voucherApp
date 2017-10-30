package pwr.groupproject.vouchers.dao;

import org.springframework.stereotype.Component;
import pwr.groupproject.vouchers.bean.model.Company;
import pwr.groupproject.vouchers.bean.model.security.UserCompany;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Component
public class UserCompanyDaoImpl implements UserCompanyDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public UserCompany getUserByUserName(String userName) {
        TypedQuery<UserCompany> query= entityManager.createQuery("FROM "+ UserCompany.class.getName()+" WHERE userName='"+userName+"'", UserCompany.class);
        try {
            return query.getSingleResult();
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
        if(query.getSingleResult()!=0)
            return true;
        else
            return false;
    }

    @Override
    public boolean ifCompanyNameIsUsed(String companyName) {
        TypedQuery<Long> query= entityManager.createQuery("SELECT count(*) FROM "+ Company.class.getName()+" c WHERE c.companyName='"+companyName+"'",Long.class);
        if(query.getSingleResult()!=0)
            return true;
        else
            return false;
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
