package pwr.groupproject.vouchers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pwr.groupproject.vouchers.bean.model.User;
import pwr.groupproject.vouchers.bean.model.VoucherCode;
import pwr.groupproject.vouchers.bean.model.security.UserCompany;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

@Component
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TemplateEngine htmlTemplateEngine;
    @Autowired
    private TemplateEngine textTemplateEngine;

    @Override
    public boolean sendVerificationTokenEmail(String activationLink, Date expirationDate, String userName){
        final Context ctx = new Context();
        ctx.setVariable("link", activationLink);

        return this.send(userName,"Activate your account in Voucher app!",ctx,"tokenTemplate");
    }

    @Override
    public boolean sendPasswordResetEmail(String passwordResetLink, String userName) {

        return false;
    }

    @Override
    public boolean sendVoucherCodeEmail(VoucherCode voucher, User user) {
        final Context ctx=new Context();
        ctx.setVariable("voucherCode",voucher.getVoucherCode());

        return this.send(user.geteMail(),"subject",ctx,"vouchertemplate");
    }

    @Override
    public void sendTest(String email, String testLink, String testText) throws MessagingException {
        final Context ctx = new Context();
        ctx.setVariable("link", testLink);
        ctx.setVariable("text", testText);

        this.send(email,"subject",ctx,"simpletemplate");
    }

    private boolean send(String destinationEmail, String subject,Context ctx,String eMailTemplate) {
        MimeMessage eMailMessage= mailSender.createMimeMessage();
        MimeMessageHelper eMailMessageHelper;
        try {
            eMailMessageHelper = new MimeMessageHelper(eMailMessage, true);
            eMailMessageHelper.setTo(destinationEmail);
            eMailMessageHelper.setSubject(subject);

            final String htmlContent = this.htmlTemplateEngine.process(eMailTemplate, ctx);
            eMailMessageHelper.setText(htmlContent, true);
            mailSender.send(eMailMessage);
            return true;
        }catch (MessagingException e){
            e.printStackTrace();
            return false;
        }
    }
}
