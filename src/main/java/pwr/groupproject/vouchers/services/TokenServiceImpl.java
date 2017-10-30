package pwr.groupproject.vouchers.services;

import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pwr.groupproject.vouchers.bean.exceptions.VerificationTokenExpired;
import pwr.groupproject.vouchers.bean.exceptions.WrongTokenException;
import pwr.groupproject.vouchers.bean.model.security.PasswordResetToken;
import pwr.groupproject.vouchers.bean.model.security.UserCompany;
import pwr.groupproject.vouchers.bean.model.security.VerificationToken;
import pwr.groupproject.vouchers.dao.TokenDao;
import pwr.groupproject.vouchers.dao.UserCompanyDao;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@Transactional
public class TokenServiceImpl implements TokenService {
    private final int TOKEN_LENGTH=50;

    @Autowired
    private TokenDao tokenDao;
    @Autowired
    private UserCompanyDao userCompanyDao;

    @Override
    public void activateAccount(String tokenString) throws VerificationTokenExpired, WrongTokenException {
        VerificationToken verificationToken=tokenDao.getVerificationTokenByToken(tokenString);
        if(verificationToken == null)
            throw new WrongTokenException();

        if(verificationToken.getExpirationDate().compareTo(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))<0){
            UserCompany userCompany=verificationToken.getUserCompany();
            userCompany.setActivated(true);
            userCompanyDao.editUser(userCompany);
            tokenDao.deletVerificationToken(verificationToken);
        }else{
            throw new VerificationTokenExpired();
        }
    }

    @Override
    public VerificationToken generateNewActicationToken(UserCompany userCompany) {
        tokenDao.deleteUsersVerificationTokens(userCompany.getUserName());
        VerificationToken verificationToken=new VerificationToken();
        verificationToken.setUserCompany(userCompany);
        verificationToken.setToken(RandomString.make(TOKEN_LENGTH));

        LocalDateTime localDateTime= LocalDateTime.now();
        localDateTime.plusMonths(1);
        verificationToken.setExpirationDate(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));

        tokenDao.addVerificationToken(verificationToken);
        return verificationToken;
    }

    @Override
    public PasswordResetToken generateNewPasswordResetToken(UserCompany userCompany) {
        tokenDao.deleteUsersResetTokens(userCompany.getUserName());
        PasswordResetToken passwordResetToken=new PasswordResetToken();
        passwordResetToken.setUserCompany(userCompany);
        passwordResetToken.setToken(RandomString.make(TOKEN_LENGTH));
        tokenDao.addPasswordResetToken(passwordResetToken);
        return passwordResetToken;
    }

    @Override
    public PasswordResetToken validatePasswordResetToken(String passwordResetToken) throws WrongTokenException {
        PasswordResetToken passwordResetToken1=tokenDao.getPasswordResetTokenByToken(passwordResetToken);
        if(passwordResetToken1==null)
            throw new WrongTokenException();
        else
            return passwordResetToken1;
    }

    @Override
    public void confirmResetingPassword(String passwordResetToken) {
        tokenDao.deleteResetToken(tokenDao.getPasswordResetTokenByToken(passwordResetToken));
    }

    @Override
    public UserCompany getUserCompanyByPasswordResetToken(String passwordResetToken) {
        return tokenDao.getPasswordResetTokenByToken(passwordResetToken).getUserCompany();
    }

    @Override
    public UserCompany getUserCompanyByVerificationToken(String verificationToken) {
        return tokenDao.getVerificationTokenByToken(verificationToken).getUserCompany();
    }


}
