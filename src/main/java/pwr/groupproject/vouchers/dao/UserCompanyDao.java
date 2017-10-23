package pwr.groupproject.vouchers.dao;

import pwr.groupproject.vouchers.bean.model.security.UserCompany;
import pwr.groupproject.vouchers.bean.model.security.VerificationToken;

public interface UserCompanyDao {
    UserCompany getUserByUserName(String userName);
    void createNewUser(UserCompany userCompany);
    void editUser(UserCompany userCompany);
    UserCompany getUserCompany(int userId);
    boolean ifEmailIsUsed(String eMail);
    void deleteUserCompany(UserCompany userCompanyId);
    UserCompany getUserCompanyByCompanyId(int companyId);
}
