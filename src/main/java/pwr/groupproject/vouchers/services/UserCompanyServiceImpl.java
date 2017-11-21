package pwr.groupproject.vouchers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.webflow.action.EventFactorySupport;
import org.springframework.webflow.execution.Event;
import pwr.groupproject.vouchers.bean.form.NewUserCompanyForm;
import pwr.groupproject.vouchers.bean.form.ResetPasswordForm;
import pwr.groupproject.vouchers.bean.model.Address;
import pwr.groupproject.vouchers.bean.model.Company;
import pwr.groupproject.vouchers.bean.model.security.UserCompany;
import pwr.groupproject.vouchers.bean.model.security.UserProfile;
import pwr.groupproject.vouchers.bean.model.security.VerificationToken;
import pwr.groupproject.vouchers.dao.CompanySurveyDao;
import pwr.groupproject.vouchers.dao.UserCompanyDao;

@Service
@Transactional
public class UserCompanyServiceImpl implements UserCompanyService {
    private final int TOKEN_LENGTH=50;
    @Autowired
    private UserCompanyDao userCompanyDao;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private ShaPasswordEncoder shaPasswordEncoder;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private MailService mailService;
    @Autowired
    private CompanySurveyDao companySurveyDao;

    @Override
    public boolean addUser(NewUserCompanyForm newUserCompanyForm){
        Address address=new Address();
        address.setAddressDetails(newUserCompanyForm.getAddressDetails());
        address.setCity(newUserCompanyForm.getCity());
        address.setPostalCode(newUserCompanyForm.getPostalCode());

        Company company=new Company();
        company.setCompanyAddress(address);
        company.setCompanyName(newUserCompanyForm.getCompanyName());

        UserCompany userCompany=new UserCompany();
        UserProfile companyAuthority=userCompanyDao.getUserProfileByName("COMPANY");
        userCompany.getUserProfiles().add(companyAuthority);
        userCompany.setUsername(newUserCompanyForm.getUserName());
        userCompany.setPassword(shaPasswordEncoder.encodePassword(newUserCompanyForm.getPassword(),null));
        userCompany.setCompany(company);
        this.userCompanyDao.addUserCompany(userCompany);
        companySurveyDao.addCompany(userCompany.getCompany());

        VerificationToken verificationToken=tokenService.generateNewActivationToken(userCompany);
        mailService.sendVerificationTokenEmail(verificationToken.getToken(),verificationToken.getExpirationDate(),userCompany.getUsername());

        return true;
    }

    @Override
    public UserCompany getUserCompanyById(int userCompanyId) {
        return userCompanyDao.getUserCompany(userCompanyId);
    }

    @Override
    public UserCompany getUserByUserName(String userName) {
        return userCompanyDao.getUserByUserName(userName);
    }

    @Override
    public UserCompany getUserCompanyByCompanyId(int companyId) {
        return userCompanyDao.getUserCompanyByCompanyId(companyId);
    }

    @Override
    public void deleteUserCompany(int userCompanyId) {
        UserCompany userCompany=userCompanyDao.getUserCompany(userCompanyId);
        userCompanyDao.deleteUserCompany(userCompany);
    }


    @Override
    public void changePassword(String userName, String newHashedPassword) {
        UserCompany userCompany=userCompanyDao.getUserByUserName(userName);
        userCompany.setPassword(newHashedPassword);
        userCompanyDao.editUser(userCompany);
    }

    @Override
    public void changePassword(ResetPasswordForm resetPasswordForm) {
        UserCompany userCompany=tokenService.getUserCompanyByPasswordResetToken(resetPasswordForm.getResetPasswordToken());
        userCompany.setPassword(shaPasswordEncoder.encodePassword(resetPasswordForm.getPassword(),null));
        userCompanyDao.editUser(userCompany);
        tokenService.confirmResetingPassword(resetPasswordForm.getResetPasswordToken());
    }


    @Override
    public Event validateUserCompany(NewUserCompanyForm userCompanyForm,MessageContext messageContext) {
        MessageBuilder error = new MessageBuilder().error();
        boolean eMailIsUsed=userCompanyDao.ifEmailIsUsed(userCompanyForm.getUserName());
        boolean companyNameIsUsed=userCompanyDao.ifCompanyNameIsUsed(userCompanyForm.getCompanyName());
        if(!eMailIsUsed && !companyNameIsUsed){
            return new EventFactorySupport().success(this);
        }else if(eMailIsUsed){
            error.source("userName");
            error.defaultText(messageSource.getMessage("message.userName.isUsed",null, LocaleContextHolder.getLocale()));
            messageContext.addMessage(error.build());
        }else if(companyNameIsUsed){
            error.source("companyName");
            error.defaultText(messageSource.getMessage("message.companyName.isUsed",null, LocaleContextHolder.getLocale()));
            messageContext.addMessage(error.build());
        }
        return new EventFactorySupport().error(this);
        //should be this or userComapnyForm ?
    }
}
