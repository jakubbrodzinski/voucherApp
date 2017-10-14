package pwr.groupproject.vouchers.dao;

import pwr.groupproject.vouchers.bean.model.security.UserCompany;

public interface UserDao {
    UserCompany getUserByUserName(String userName);

}
