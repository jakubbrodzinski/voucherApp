package pwr.groupproject.vouchers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pwr.groupproject.vouchers.dao.UserCompanyDao;
import pwr.groupproject.vouchers.services.UserCompanyService;

import javax.annotation.security.PermitAll;

@Controller
@RequestMapping(AuthController.ROOT_MAPPING)
@PermitAll
public class AuthController {
    public static final String ROOT_MAPPING="/";
    @Autowired
    private AuthenticationTrustResolver authenticationTrustResolver;

    @RequestMapping("sign_in")
    public String signIn(){
        if(getPrincipal()==null)
            return "auth/sign_in.html";
        else
            return "redirect:"+"/my_account/home";
    }

    @Autowired
    private UserCompanyDao userCompanyDao;
    @RequestMapping("/home")
    @ResponseBody
    public String xyz(){
        return userCompanyDao.ifCompanyNameIsUsed("xyz") + " " + userCompanyDao.ifCompanyNameIsUsed("companyA") + "\n"+userCompanyDao.ifEmailIsUsed("xyz@xyz.com2")+' '+userCompanyDao.ifEmailIsUsed("xyz@xyz.com");
    }

    private String getPrincipal() {
        String userName = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            userName = authentication.getName();
        }
        return userName;
    }

    private boolean isCurrentAuthenticationAnonymous() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authenticationTrustResolver.isAnonymous(authentication);
    }
}
