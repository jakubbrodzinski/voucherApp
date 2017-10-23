package pwr.groupproject.vouchers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pwr.groupproject.vouchers.bean.exceptions.VerificationTokenExpired;
import pwr.groupproject.vouchers.bean.model.security.VerificationToken;
import pwr.groupproject.vouchers.dao.TokenDao;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@Transactional
public class TokenServiceImpl implements TokenService {
    @Autowired
    private TokenDao tokenDao;

    @Override
    public void activateAccount(String activationToken) throws VerificationTokenExpired{
        VerificationToken verificationToken=tokenDao.getVerificationTokenByToken(activationToken);
        if(verificationToken.getExpirationDate().compareTo(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))<0){
            verificationToken.getUserCompany().setActivated(true);
        }else{
            throw new VerificationTokenExpired();
        }
    }

    @Override
    public void generateNewActicationToken(String username) {

    }
}
