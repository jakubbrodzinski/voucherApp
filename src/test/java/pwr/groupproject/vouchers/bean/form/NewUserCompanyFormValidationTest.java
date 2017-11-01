package pwr.groupproject.vouchers.bean.form;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.binding.message.MessageContext;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import pwr.groupproject.vouchers.bean.model.Company;
import pwr.groupproject.vouchers.bean.model.security.UserCompany;
import pwr.groupproject.vouchers.configuration.*;
import pwr.groupproject.vouchers.dao.UserCompanyDao;
import pwr.groupproject.vouchers.dao.UserCompanyDaoImpl;
import pwr.groupproject.vouchers.services.CustomUserDetailsServiceImpl;
import pwr.groupproject.vouchers.services.UserCompanyService;
import pwr.groupproject.vouchers.services.UserCompanyServiceImpl;

import javax.validation.ConstraintViolation;

import java.util.Locale;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfiguration.class,SpringSecurityConfiguration.class,SpringWebFlowConfiguration.class, WebConfiguration.class, UserCompanyServiceImpl.class,UserCompanyDaoImpl.class, CustomUserDetailsServiceImpl.class,})
public class NewUserCompanyFormValidationTest {
    @Autowired
    @Qualifier("localValidatorFactoryBean")
    private LocalValidatorFactoryBean validator;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private UserCompanyService userCompanyService;
    @Autowired
    private UserCompanyDao userCompanyDao;

    @Test
    public void dependencyInjectionTest(){
        Assert.assertNotNull(validator);
    }

    @Test
    public void newUserCompanyFormValidatingTest(){
        NewUserCompanyForm form1=new NewUserCompanyForm();
        Set<ConstraintViolation<NewUserCompanyForm>> violations1=validator.validate(form1,NewUserCompanyForm.ValidationGroup1.class);
        Assert.assertEquals(5,violations1.size());
        form1.setPassword("xyzxyzxyz");
        form1.setRepeatedPassword("xyzxyzxyz");
        form1.setCompanyName("cvxzxcvxxz cvxz");
        form1.setUserName("abcabc");
        violations1=validator.validate(form1,NewUserCompanyForm.ValidationGroup1.class);
        Assert.assertEquals(1,violations1.size());
        form1.setPostalCode("333-zxv");
        form1.setCompanyName("śćą sdfvzvxzc");
        form1.setAddressDetails("!@#!");
        violations1=validator.validate(form1,NewUserCompanyForm.ValidationGroup2.class);
        Assert.assertEquals(2,violations1.size());
        form1.setPostalCode("21-123");
        form1.setAddressDetails("vczx, vcxzvz");
        form1.setCompanyName("1#@$#@");
        violations1=validator.validate(form1,NewUserCompanyForm.ValidationGroup2.class);
        Assert.assertEquals(0,violations1.size());

    }

    @Test
    public void messageSourceTest(){
        Assert.assertNotNull(messageSource.getMessage("message.companyName.isUsed",null, Locale.forLanguageTag("pl_PL")));
        Assert.assertNotNull(messageSource.getMessage("message.userName.isUsed",null, Locale.forLanguageTag("pl_PL")));
        Assert.assertNotNull(messageSource.getMessage("message.companyName.isUsed",null, Locale.ENGLISH));
        Assert.assertNotNull(messageSource.getMessage("message.userName.isUsed",null, Locale.ENGLISH));
    }

    @Test(expected = Throwable.class)
    public void testByService() throws Throwable {
        UserCompanyDao userCompanyDaoMock=Mockito.mock(UserCompanyDaoImpl.class);
        Mockito.doReturn(true).when(userCompanyDaoMock).ifEmailIsUsed(Mockito.any());
        Mockito.doReturn(true).when(userCompanyDaoMock).ifCompanyNameIsUsed(Mockito.any());
        MessageContext messageContext=Mockito.mock(org.springframework.binding.message.MessageContext.class);
        NewUserCompanyForm userCompanyForm= new NewUserCompanyForm();

        try {
            userCompanyService.validateUserCompany(userCompanyForm, messageContext);
            throw new Throwable() {};
        }catch(NoSuchMessageException ex){
            ex.printStackTrace();
        }
    }

    @Test
    @Transactional
    public void testValidatingByDao(){
        Assert.assertFalse(userCompanyDao.ifEmailIsUsed("-1"));
        Assert.assertFalse(userCompanyDao.ifCompanyNameIsUsed("-1"));
        UserCompany userCompany=new UserCompany();
        userCompany.setUserName("xyz@xyz.com");
        Company company=new Company();
        company.setCompanyName("companyA");
        userCompany.setCompany(company);
        userCompanyDao.addUserCompany(userCompany);
        Assert.assertTrue(userCompanyDao.ifEmailIsUsed("xyz@xyz.com"));
        Assert.assertTrue(userCompanyDao.ifCompanyNameIsUsed("companyA"));
    }
}
