package pwr.groupproject.vouchers.dao;

import org.springframework.stereotype.Component;
import pwr.groupproject.vouchers.bean.model.security.PasswordResetToken;
import pwr.groupproject.vouchers.bean.model.security.UserCompany;
import pwr.groupproject.vouchers.bean.model.security.VerificationToken;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class TokenDaoImpl implements TokenDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PasswordResetToken getPasswordResetTokenByToken(String resetToken) {
        return entityManager.createQuery("FROM " + PasswordResetToken.class.getName() + " t WHERE t.token='" + resetToken + "'", PasswordResetToken.class).getSingleResult();
    }

    @Override
    public PasswordResetToken getPasswordResetTokenById(int tokenId) {
        return entityManager.find(PasswordResetToken.class, tokenId);
    }

    @Override
    public void deleteResetToken(PasswordResetToken passwordResetToken) {
        entityManager.remove(passwordResetToken);
    }

    @Override
    public void addPasswordResetToken(PasswordResetToken passwordResetToken) {
        entityManager.persist(passwordResetToken);
    }

    @Override
    public VerificationToken getVerificationTokenByToken(String verificationToken) {
        try {
            return entityManager.createQuery("FROM " + VerificationToken.class.getName() + " WHERE token='" + verificationToken + "'", VerificationToken.class).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public VerificationToken getVerificationTokenById(int tokenId) {
        return entityManager.find(VerificationToken.class, tokenId);
    }

    @Override
    public void deleteVerificationToken(VerificationToken verificationToken) {
        entityManager.remove(verificationToken);
    }

    @Override
    public void addVerificationToken(VerificationToken verificationToken) {
        entityManager.persist(verificationToken);
    }

    @Override
    public void deleteUsersResetTokens(String userName) {
        List<PasswordResetToken> resultList = entityManager.createQuery("SELECT t FROM " + PasswordResetToken.class.getName() + " t JOIN " + UserCompany.class.getName() + " u ON t.userCompany=u.Id WHERE u.userName='" + userName + "'", PasswordResetToken.class).getResultList();
        resultList.forEach(this::deleteResetToken);
    }

    @Override
    public void deleteUsersVerificationTokens(String userName) {
        List<VerificationToken> resultList = entityManager.createQuery("SELECT  v FROM " + VerificationToken.class.getName() + " v JOIN " + UserCompany.class.getName() + " u ON v.userCompany=u.Id WHERE u.userName='" + userName + "'", VerificationToken.class).getResultList();
        resultList.forEach(this::deleteVerificationToken);
    }

    @Override
    public void deleteUsersResetTokens(int userCompanyId) {
        try {
            List<PasswordResetToken> resetTokens = entityManager.createQuery("FROM " + PasswordResetToken.class.getName() + " WHERE userCompany=" + userCompanyId, PasswordResetToken.class).getResultList();
            resetTokens.forEach(this::deleteResetToken);
        }catch(NoResultException e){
        }
    }

    @Override
    public void deleteUsersVerificationTokens(int userCompanyId) {
        try{
            List<VerificationToken> resultList=entityManager.createQuery("FROM "+VerificationToken.class.getName()+" WHERE userCompany="+userCompanyId,VerificationToken.class).getResultList();
            resultList.forEach(this::deleteVerificationToken);
        }catch(NoResultException e){}
    }
}
