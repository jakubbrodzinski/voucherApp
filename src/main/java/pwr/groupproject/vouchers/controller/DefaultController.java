package pwr.groupproject.vouchers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pwr.groupproject.vouchers.bean.model.User;
import pwr.groupproject.vouchers.bean.model.VoucherCode;
import pwr.groupproject.vouchers.services.MailService;

@Controller
public class DefaultController {

    @Autowired
    private MailService mailService;

    @RequestMapping("/")
    public String home() {
        return "index";
    }

    @RequestMapping("/testmail")
    public String testmail() {
        VoucherCode voucherCode=new VoucherCode();
        voucherCode.setVoucherCode("codecodecode");
        User user=new User();
        user.seteMail("jakubby@gmail.com");
        mailService.sendVoucherCodeEmail(voucherCode,user);
        return "index";
    }
}
