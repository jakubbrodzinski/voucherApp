package pwr.groupproject.vouchers.dao;

import org.springframework.stereotype.Component;
import pwr.groupproject.vouchers.bean.model.security.PasswordResetToken;
import pwr.groupproject.vouchers.bean.model.security.VerificationToken;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class TokenDaoImpl implements TokenDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PasswordResetToken getPasswordResetTokenByToken(String resetToken) {
        return entityManager.createQuery("FROM "+PasswordResetToken.class.getName() + " WHERE token='"+resetToken+"'",PasswordResetToken.class).getSingleResult();
    }

    @Override
    public PasswordResetToken getPasswordResetTokenById(int tokenId) {
        return entityManager.createQuery("FROM "+PasswordResetToken.class.getName() + " WHERE id='"+tokenId+"'",PasswordResetToken.class).getSingleResult();
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
        return entityManager.createQuery("FROM "+VerificationToken.class.getName() + " WHERE token='"+verificationToken+"'",VerificationToken.class).getSingleResult();
    }

    @Override
    public VerificationToken getVerificationTokenById(int tokenId) {
        return entityManager.createQuery("FROM "+VerificationToken.class.getName() + " WHERE id='"+tokenId+"'",VerificationToken.class).getSingleResult();
    }

    @Override
    public void deletVerificationToken(VerificationToken verificationToken) {
        entityManager.remove(verificationToken);
    }

    @Override
    public void addVerificationToken(VerificationToken verificationToken) {
        entityManager.persist(verificationToken);
    }
}
