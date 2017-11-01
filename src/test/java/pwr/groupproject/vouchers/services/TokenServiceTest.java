package pwr.groupproject.vouchers.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import pwr.groupproject.vouchers.bean.exceptions.VerificationTokenExpired;
import pwr.groupproject.vouchers.bean.exceptions.WrongTokenException;
import pwr.groupproject.vouchers.bean.model.security.PasswordResetToken;
import pwr.groupproject.vouchers.bean.model.security.UserCompany;
import pwr.groupproject.vouchers.bean.model.security.VerificationToken;
import pwr.groupproject.vouchers.configuration.HibernateConfiguration;
import pwr.groupproject.vouchers.dao.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfiguration.class,UserCompanyDaoImpl.class, TokenDaoImpl.class,TokenServiceImpl.class})
public class TokenServiceTest {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private TokenDao tokenDao;
    @Autowired
    private UserCompanyDao userCompanyDao;

    @Test
    public void testWiring(){
        Assert.assertNotNull(tokenDao);
        Assert.assertNotNull(tokenService);
        Assert.assertNotNull(userCompanyDao);
    }

    @Test(expected = VerificationTokenExpired.class)
    @Transactional
    public void activateAccount() throws Exception, VerificationTokenExpired {
        UserCompany userCompany1=new UserCompany();
        userCompany1.setPassword("abc1");
        userCompany1.setUserName("username1");

        LocalDateTime localDateTime1= LocalDateTime.now().minusMonths(2);
        Date date1= Date.from(localDateTime1.atZone(ZoneId.systemDefault()).toInstant());

        VerificationToken verificationToken1=new VerificationToken();
        verificationToken1.setUserCompany(userCompany1);
        verificationToken1.setToken("1");
        verificationToken1.setExpirationDate(date1);

        userCompanyDao.addUserCompany(userCompany1);
        tokenDao.addVerificationToken(verificationToken1);

        try {
            tokenService.activateAccount("1");
        }catch(WrongTokenException e){
            e.printStackTrace();
        }
    }

    @Test
    @Transactional
    public void activateAccountOK() throws Exception {
        UserCompany userCompany2=new UserCompany();
        userCompany2.setPassword("abc2");
        userCompany2.setUserName("username2");

        LocalDateTime localDateTime2= LocalDateTime.now().plusMinutes(10);
        Date date2= Date.from(localDateTime2.atZone(ZoneId.systemDefault()).toInstant());

        VerificationToken verificationToken2=new VerificationToken();
        verificationToken2.setUserCompany(userCompany2);
        verificationToken2.setToken("2");
        verificationToken2.setExpirationDate(date2);

        userCompanyDao.addUserCompany(userCompany2);
        tokenDao.addVerificationToken(verificationToken2);
        try {
            tokenService.activateAccount("2");
        }catch(WrongTokenException e){
            e.printStackTrace();
        }catch (VerificationTokenExpired ex){
            ex.printStackTrace();
        }

        Assert.assertTrue(userCompanyDao.getUserByUserName("username2").isActivated());

    }

    @Test
    @Transactional
    public void generateNewActivationToken() throws Exception {
        UserCompany userCompany1=new UserCompany();
        userCompany1.setPassword("abc1");
        userCompany1.setUserName("username1");

        LocalDateTime localDateTime1= LocalDateTime.now().minusMonths(2);
        Date date1= Date.from(localDateTime1.atZone(ZoneId.systemDefault()).toInstant());

        VerificationToken verificationToken1=new VerificationToken();
        verificationToken1.setUserCompany(userCompany1);
        verificationToken1.setToken("1");
        verificationToken1.setExpirationDate(date1);

        userCompanyDao.addUserCompany(userCompany1);
        tokenDao.addVerificationToken(verificationToken1);

        VerificationToken properToken=tokenService.generateNewActivationToken(userCompany1);
        Throwable throwable=null;
        try{
            tokenService.activateAccount("1");
        }catch (Throwable e) {
            throwable=e;
        }
        Assert.assertTrue(throwable instanceof  WrongTokenException);

        try {
            tokenService.activateAccount(properToken.getToken());
        }catch (Throwable e){

        }
        Assert.assertTrue(userCompanyDao.getUserByUserName("username1").isActivated());
    }

    @Test(expected = Throwable.class)
    public void generateNewPasswordResetToken() throws Exception {
        UserCompany userCompany1=new UserCompany();
        userCompany1.setPassword("abc1");
        userCompany1.setUserName("username1");

        PasswordResetToken passwordResetToken1=new PasswordResetToken();
        passwordResetToken1.setUserCompany(userCompany1);
        passwordResetToken1.setToken("1");

        userCompanyDao.addUserCompany(userCompany1);
        tokenDao.addPasswordResetToken(passwordResetToken1);

        PasswordResetToken properToken=tokenService.generateNewPasswordResetToken(userCompany1);
        Throwable throwable=null;
        try{
            tokenService.validatePasswordResetToken("1");
        }catch (Throwable e) {
            throwable=e;
        }
        Assert.assertTrue(throwable instanceof  WrongTokenException);

        try {
            tokenService.validatePasswordResetToken(properToken.getToken());
            throw new Throwable();
        }catch (Throwable e){

        }

    }

}