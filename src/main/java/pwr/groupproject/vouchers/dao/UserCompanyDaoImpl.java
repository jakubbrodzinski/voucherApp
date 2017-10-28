package pwr.groupproject.vouchers.dao;

import org.springframework.stereotype.Component;
import pwr.groupproject.vouchers.bean.model.Company;
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
    public UserCompany getUserCompany(int userId) {
        return entityManager.find(UserCompany.class,userId);
    }

    @Override
    public boolean ifEmailIsUsed(String eMail) {
        TypedQuery<Integer> query= entityManager.createQuery("SELCT count(*) FROM "+ UserCompany.class.getName()+" uc WHERE uc.userName='"+eMail+"'",Integer.class);
        if(query.getSingleResult()!=0)
            return false;
        else
            return true;
    }

    @Override
    public boolean ifCompanyNameIsUsed(String companyName) {
        TypedQuery<Integer> query= entityManager.createQuery("SELCT count(*) FROM "+ Company.class.getName()+"c WHERE c.companyName='"+companyName+"'",Integer.class);
        if(query.getSingleResult()!=0)
            return false;
        else
            return true;
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
