package pwr.groupproject.vouchers.services;

import pwr.groupproject.vouchers.bean.model.security.UserCompany;

public interface UserCompanyService {
    void addUser(UserCompany userCompany);

    boolean ifEmailIsUsed(String eMail);
    void changeEmail(String userName,String newEmail);
    void changePassword(String userName,String newHashedPassword);
}
