package pwr.groupproject.vouchers.dao;

import pwr.groupproject.vouchers.bean.model.security.UserCompany;
import pwr.groupproject.vouchers.bean.model.security.VerificationToken;

public interface UserDao {
    UserCompany getUserByUserName(String userName);
    void createNewUser(UserCompany userCompany);
    void editUser(UserCompany userCompany);
    UserCompany getUser(int userId);
    boolean ifEmailIsUsed(String eMail);
}
