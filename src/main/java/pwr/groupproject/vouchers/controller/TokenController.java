package pwr.groupproject.vouchers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pwr.groupproject.vouchers.bean.enums.TokenStatus;
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
    static final String ROOT_MAPPING = "/token";
    private final MailService mailService;
    private final TokenService tokenService;
    private final UserCompanyService userCompanyService;

    @Autowired
    public TokenController(MailService mailService, TokenService tokenService, UserCompanyService userCompanyService) {
        this.mailService = mailService;
        this.tokenService = tokenService;
        this.userCompanyService = userCompanyService;
    }

    @RequestMapping(value = "/reset_password", method = RequestMethod.GET)
    public String resetPassword(@RequestParam("t") String token, Model model) {
        try {
            tokenService.validatePasswordResetToken(token);
            ResetPasswordForm form = new ResetPasswordForm();
            form.setResetPasswordToken(token);
            model.addAttribute("resetPasswordForm", form);
            model.addAttribute("tokenStatus", TokenStatus.OK);
        } catch (WrongTokenException ex) {
            model.addAttribute("tokenStatus", TokenStatus.WRONG);
        }
        return "token/reset_password.html";
    }

    @RequestMapping(value = "/reset_password", method = RequestMethod.POST)
    public String resetPassword(@ModelAttribute @Validated ResetPasswordForm resetPasswordForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            try {
                tokenService.validatePasswordResetToken(resetPasswordForm.getResetPasswordToken());
                model.addAttribute("tokenStatus", TokenStatus.OK);
            } catch (WrongTokenException ex) {
                model.addAttribute("tokenStatus", TokenStatus.WRONG);
            }
            return "token/reset_password.html";
        }
        userCompanyService.changePassword(resetPasswordForm);
        return "token/reset_password_success.html";
    }

    @RequestMapping(value = "/activate_account", method = RequestMethod.GET)
    public String activateAccount(@RequestParam("t") String token, Model model, RedirectAttributes redirectAttributes
    ) {
        try {
            tokenService.activateAccount(token);
            redirectAttributes.addAttribute("acc",2);
        } catch (WrongTokenException e) {
            redirectAttributes.addAttribute("acc",3);
        } catch (VerificationTokenExpired e2) {
            UserCompany userCompany = tokenService.getUserCompanyByVerificationToken(token);
            VerificationToken newToken = tokenService.generateNewActivationToken(userCompany);
            mailService.sendVerificationTokenEmail(newToken.getToken(), newToken.getExpirationDate(), userCompany.getUsername());
            redirectAttributes.addAttribute("acc",4);
        }

        return "redirect:"+"/";
    }


}