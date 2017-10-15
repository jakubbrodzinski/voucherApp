package pwr.groupproject.vouchers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pwr.groupproject.vouchers.bean.model.security.UserCompany;
import pwr.groupproject.vouchers.dao.UserCompanyDao;

@Service
@Transactional
public class UserCompanyServiceImpl implements UserCompanyService {
    @Autowired
    private UserCompanyDao userCompanyDao;

    @Override
    public void addUser(pwr.groupproject.vouchers.bean.model.security.UserCompany userCompany){
        this.userCompanyDao.createNewUser(userCompany);
        return ;
    }

    @Override
    public boolean ifEmailIsUsed(String eMail) {
        return userCompanyDao.ifEmailIsUsed(eMail);
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    public void changeEmail(String userName, String newEmail) {
        UserCompany userCompany=userCompanyDao.getUserByUserName(userName);
        userCompany.seteMail(newEmail);
        userCompanyDao.editUser(userCompany);
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    public void changePassword(String userName, String newHashedPassword) {
        UserCompany userCompany=userCompanyDao.getUserByUserName(userName);
        userCompany.setPassword(newHashedPassword);
        userCompanyDao.editUser(userCompany);
    }
}
