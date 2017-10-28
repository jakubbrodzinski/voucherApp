package pwr.groupproject.vouchers.services;

import org.springframework.binding.message.MessageContext;
import org.springframework.webflow.execution.Event;
import pwr.groupproject.vouchers.bean.form.NewUserCompanyForm;
import pwr.groupproject.vouchers.bean.model.security.UserCompany;


public interface UserCompanyService {
    void addUser(NewUserCompanyForm newUserCompanyForm);
    UserCompany getUserCompanyById(int userCompanyId);
    UserCompany getUserCompanyByCompanyId(int companyId);
    void deleteUserCompany(int userCompanyId);

    void changePassword(String userName,String newHashedPassword);

    Event validateUserCompany(NewUserCompanyForm userCompanyForm, MessageContext messageContext);
}
