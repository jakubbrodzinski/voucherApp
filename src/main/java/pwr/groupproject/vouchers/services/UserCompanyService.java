package pwr.groupproject.vouchers.services;

import pwr.groupproject.vouchers.bean.model.security.UserCompany;

public interface UserCompanyService {
    void addUser(UserCompany userCompany);
    UserCompany getUserCompanyById(int userCompanyId);
    UserCompany getUserCompanyByCompanyId(int companyId);
    void deleteUserCompany(int userCompanyId);

    boolean ifEmailIsUsed(String eMail);
    void changeEmail(String userName,String newEmail);
    void changePassword(String userName,String newHashedPassword);
}
