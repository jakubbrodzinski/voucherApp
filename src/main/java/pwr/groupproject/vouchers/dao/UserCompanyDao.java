package pwr.groupproject.vouchers.dao;

import pwr.groupproject.vouchers.bean.model.security.UserCompany;

public interface UserCompanyDao {
    UserCompany getUserByUserName(String userName);
    void createNewUser(UserCompany userCompany);
    void editUser(UserCompany userCompany);
    UserCompany getUserCompany(int userId);
    boolean ifEmailIsUsed(String eMail);
    boolean ifCompanyNameIsUsed(String companyName);
    void deleteUserCompany(UserCompany userCompanyId);
    UserCompany getUserCompanyByCompanyId(int companyId);
}
