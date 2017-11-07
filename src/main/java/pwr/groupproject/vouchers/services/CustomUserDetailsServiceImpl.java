package pwr.groupproject.vouchers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pwr.groupproject.vouchers.bean.model.security.UserCompany;
import pwr.groupproject.vouchers.dao.UserCompanyDao;


@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserCompanyDao userCompanyDao;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCompany userCompany= this.userCompanyDao.getUserByUserName(username);
        if(userCompany==null)
            throw new UsernameNotFoundException("NOT FOUND LOGIN: \""+username+"\"");
        else
            return userCompany;

    }
}
