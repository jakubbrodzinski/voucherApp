package pwr.groupproject.vouchers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pwr.groupproject.vouchers.bean.exceptions.VerificationTokenExpired;
import pwr.groupproject.vouchers.bean.exceptions.WrongTokenException;
import pwr.groupproject.vouchers.bean.form.ResetPasswordForm;
import pwr.groupproject.vouchers.bean.model.security.UserCompany;
import pwr.groupproject.vouchers.bean.model.security.VerificationToken;
import pwr.groupproject.vouchers.services.MailService;
import pwr.groupproject.vouchers.services.TokenService;
import pwr.groupproject.vouchers.services.UserCompanyService;

@Controller
@RequestMapping(TokenController.ROOT_MAPPING)
public class TokenController {
    public static final String ROOT_MAPPING = "/token";

    @Autowired
    private MailService mailService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserCompanyService userCompanyService;
    @Autowired
    private ShaPasswordEncoder shaPasswordEncoder;

    @RequestMapping(value = "/reset_password", method = RequestMethod.GET)
    public String resetPassowrd(@RequestParam("t") String token, Model model) {
        try {
            tokenService.validatePasswordResetToken(token);
            ResetPasswordForm form = new ResetPasswordForm();
            form.setPasswordToken(token);
            model.addAttribute("resetPasswordForm", token);
            return "";
        } catch (WrongTokenException ex) {
            return "";
        }
    }

    @RequestMapping(value = "/reset_password", method = RequestMethod.POST)
    public String resetPassowrd(@RequestParam("t") String token, @ModelAttribute @Validated ResetPasswordForm resetPasswordForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "";
        UserCompany userCompany = tokenService.getUserCompanyByPasswordResetToken(resetPasswordForm.getPasswordToken());
        userCompanyService.changePassword(userCompany, resetPasswordForm);
        return "";
    }

    //Maybe instead of 3xreturn we can create one website where we put TH:IF and add to the Model class something like status?
    //Status.OK/EXPIRED/WRONG
    @RequestMapping(value = "/activate_account", method = RequestMethod.GET)
    public String activateAccount(@RequestParam("t") String token) {
        try {
            tokenService.activateAccount(token);
        } catch (WrongTokenException e) {
            return "";
        } catch (VerificationTokenExpired e2) {
            UserCompany userCompany=tokenService.getUserCompanyByVerificationToken(token);
            VerificationToken newToken = tokenService.generateNewActicationToken(userCompany);
            mailService.sendVerificationTokenEmail(newToken.getToken(),newToken.getExpirationDate(),userCompany.getUserName());
            return "";
        }

        return "";
    }


}