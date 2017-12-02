package pwr.groupproject.vouchers.dao;

import pwr.groupproject.vouchers.bean.model.Company;
import pwr.groupproject.vouchers.bean.model.security.UserCompany;
import pwr.groupproject.vouchers.bean.model.security.UserProfile;

public interface UserCompanyDao {
    UserCompany getUserByUserName(String userName);

    void addUserCompany(UserCompany userCompany);

    void editUser(UserCompany userCompany);

    UserCompany getUserCompany(int userId);

    boolean ifEmailIsUsed(String eMail);

    boolean ifCompanyNameIsUsed(String companyName);

    void deleteUserCompany(UserCompany userCompanyId);

    UserCompany getUserCompanyByCompanyId(int companyId);

    UserProfile getUserProfileByName(String userProfile);
}
