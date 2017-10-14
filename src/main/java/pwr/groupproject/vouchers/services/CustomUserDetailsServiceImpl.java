package pwr.groupproject.vouchers.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pwr.groupproject.vouchers.bean.model.security.UserCompany;
import pwr.groupproject.vouchers.dao.UserDao;

import javax.persistence.NoResultException;


@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserDao userDao;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try{
            UserCompany userCompany= userDao.getUserByUserName(username);
            System.out.println(userCompany.toString());
            return new User(userCompany.getUserName(),userCompany.getPassword(),userCompany.isActivated(),true,true,true,userCompany.getUserProfiles());
        }catch(NoResultException ex){
            throw new UsernameNotFoundException("NOT FOUND LOGIN: \""+username+"\"");
        }
    }
}
