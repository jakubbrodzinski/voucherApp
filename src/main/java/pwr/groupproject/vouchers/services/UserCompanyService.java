package pwr.groupproject.vouchers.services;

import org.springframework.binding.message.MessageContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.webflow.execution.Event;
import pwr.groupproject.vouchers.bean.form.NewUserCompanyForm;
import pwr.groupproject.vouchers.bean.form.ResetPasswordForm;
import pwr.groupproject.vouchers.bean.model.Company;
import pwr.groupproject.vouchers.bean.model.security.UserCompany;


public interface UserCompanyService {
    boolean addUser(NewUserCompanyForm newUserCompanyForm);

    @PreAuthorize("hasRole('COMPANY')")
    UserCompany getUserCompanyById(int userCompanyId);

    @PreAuthorize("hasRole('COMPANY')")
    UserCompany getUserByUserName(String userName);

    @PreAuthorize("hasRole('COMPANY')")
    UserCompany getUserCompanyByCompanyId(int companyId);

    @PreAuthorize("hasRole('COMPANY')")
    void deleteUserCompany(int userCompanyId);

    @PreAuthorize("hasRole('COMPANY')")
    void changePassword(String userName, String newHashedPassword);

    void changePassword(ResetPasswordForm resetPasswordForm);

    Event validateUserCompany(NewUserCompanyForm userCompanyForm, MessageContext messageContext);
}
