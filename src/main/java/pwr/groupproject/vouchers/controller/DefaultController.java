package pwr.groupproject.vouchers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pwr.groupproject.vouchers.bean.enums.TokenStatus;
import pwr.groupproject.vouchers.bean.form.ResetPasswordForm;
import pwr.groupproject.vouchers.bean.model.User;
import pwr.groupproject.vouchers.bean.model.VoucherCode;
import pwr.groupproject.vouchers.bean.model.security.PasswordResetToken;
import pwr.groupproject.vouchers.services.MailService;

import java.util.Date;

@Controller
public class DefaultController {

    @Autowired
    private MailService mailService;

    @RequestMapping("/testmail")
    public String testmail() {
        VoucherCode voucherCode=new VoucherCode();
        voucherCode.setVoucherCode("codecodecode");
        User user=new User();
        user.seteMail("jakubby@gmail.com");
        mailService.sendVoucherCodeEmail(voucherCode,user);
        mailService.sendPasswordResetEmail("token","jakubby@gmail.com");
        mailService.sendVerificationTokenEmail("token",new Date(),"jakubby@gmail.com");
        return "index";
    }

    @RequestMapping(value = "/xyz",method = RequestMethod.GET)
    public String hello(Model model){
        ResetPasswordForm form=new ResetPasswordForm();
        form.setResetPasswordToken("token");
        model.addAttribute("resetPasswordForm",form);
        model.addAttribute("tokenStatus", TokenStatus.OK);
        return "/token/reset_password.html";
    }

    @RequestMapping(value = "/xyz",method = RequestMethod.POST)
    @ResponseBody
    public String hello2(@ModelAttribute ResetPasswordForm form){
        return form.getResetPasswordToken();
    }
}
