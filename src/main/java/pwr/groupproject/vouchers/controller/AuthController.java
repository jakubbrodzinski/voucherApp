package pwr.groupproject.vouchers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pwr.groupproject.vouchers.bean.enums.ErrorCode;
import pwr.groupproject.vouchers.bean.form.ForgotPasswordForm;
import pwr.groupproject.vouchers.bean.model.security.PasswordResetToken;
import pwr.groupproject.vouchers.bean.model.security.UserCompany;
import pwr.groupproject.vouchers.services.MailService;
import pwr.groupproject.vouchers.services.TokenService;
import pwr.groupproject.vouchers.services.UserCompanyService;

import javax.annotation.security.PermitAll;

@Controller
@RequestMapping(AuthController.ROOT_MAPPING)
@PermitAll
public class AuthController {
    public static final String ROOT_MAPPING = "/";
    @Autowired
    private AuthenticationTrustResolver authenticationTrustResolver;
    @Autowired
    private MailService mailService;
    @Autowired
    private UserCompanyService userCompanyService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private TokenService tokenService;

    @RequestMapping("/")
    public String homePage() {
        return "/index.html";
    }

    @RequestMapping("acc_denied")
    public String accessDenied() {
        return "/acc_denied.html";
    }

    @RequestMapping(value = "sign_in", method = RequestMethod.GET)
    public String signIn(@RequestParam(name = "error", required = false) Integer errorCode, Model model) {
        if (getPrincipal() == null) {
            if (errorCode != null)
                model.addAttribute("errorCode", ErrorCode.getCodeByStatus(errorCode));
            return "auth/sign_in.html";
        } else {
            return "redirect:" + "/my_account/home";
        }
    }

    @RequestMapping(value = "forgot_password", method = RequestMethod.GET)
    public String forgottenPassword(Model model) {
        model.addAttribute("form", new ForgotPasswordForm());
        return "auth/forgot_password.html";
    }

    @RequestMapping(value = "forgot_password", method = RequestMethod.POST)
    public String forgottenPassword(@ModelAttribute(name = "form") @Validated ForgotPasswordForm forgotPasswordForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "auth/forgot_password.html";

        UserCompany userCompany = userCompanyService.getUserByUserName(forgotPasswordForm.getUserName());
        if (userCompany == null) {
            bindingResult.rejectValue("userName", "email.dont.exist", messageSource.getMessage("message.wrong.email", null, LocaleContextHolder.getLocale()));
            return "auth/forgot_password.html";
        } else if (!userCompany.isEnabled()) {
            bindingResult.rejectValue("userName", "account.not.activated",messageSource.getMessage("messages.account.not.activated", null, LocaleContextHolder.getLocale()));
            return "auth/forgot_password.html";
        } else {
            PasswordResetToken passwordResetToken = tokenService.generateNewPasswordResetToken(userCompany);
            mailService.sendPasswordResetEmail(passwordResetToken.getToken(), userCompany.getUsername());
        }

        return "auth/forgot_password_sent.html";
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
