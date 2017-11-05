package pwr.groupproject.vouchers.services;

import org.springframework.binding.message.MessageContext;
import org.springframework.webflow.execution.Event;
import pwr.groupproject.vouchers.bean.form.NewUserCompanyForm;
import pwr.groupproject.vouchers.bean.form.ResetPasswordForm;
import pwr.groupproject.vouchers.bean.model.security.UserCompany;


public interface UserCompanyService {
    boolean addUser(NewUserCompanyForm newUserCompanyForm);
    UserCompany getUserCompanyById(int userCompanyId);
    UserCompany getUserByUserName(String userName);
    UserCompany getUserCompanyByCompanyId(int companyId);
    void deleteUserCompany(int userCompanyId);

    void changePassword(String userName,String newHashedPassword);
    void changePassword(ResetPasswordForm resetPasswordForm);

    Event validateUserCompany(NewUserCompanyForm userCompanyForm, MessageContext messageContext);
}
