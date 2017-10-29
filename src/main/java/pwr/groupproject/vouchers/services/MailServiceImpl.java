package pwr.groupproject.vouchers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pwr.groupproject.vouchers.bean.model.User;
import pwr.groupproject.vouchers.bean.model.Voucher;
import pwr.groupproject.vouchers.bean.model.VoucherCode;
import pwr.groupproject.vouchers.bean.model.security.UserCompany;
import pwr.groupproject.vouchers.bean.model.security.VerificationToken;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TemplateEngine htmlTemplateEngine;
    @Autowired
    private TemplateEngine textTemplateEngine;

    @Override
    public void sendVerificationTokenEmail(String activationLink, UserCompany company) throws MessagingException {
        final Context ctx = new Context();
        ctx.setVariable("link", activationLink);

        this.send(company.getUserName(),"Activate your account in Voucher app!",ctx,"tokenTemplate");
    }

    @Override
    public void sendPasswordResetEmail(String passwordResetLink, UserCompany company) throws MessagingException {

    }

    @Override
    public void sendVoucherCodeEmail(VoucherCode voucher, User user) throws MessagingException {
        final Context ctx=new Context();
        ctx.setVariable("voucherCode",voucher.getVoucherCode());

        this.send(user.geteMail(),"subject",ctx,"vouchertemplate");
    }

    @Override
    public void sendTest(String email, String testLink, String testText) throws MessagingException {
        final Context ctx = new Context();
        ctx.setVariable("link", testLink);
        ctx.setVariable("text", testText);

        this.send(email,"subject",ctx,"simpletemplate");
    }

    private void send(String destinationEmail, String subject,Context ctx,String eMailTemplate) throws  MessagingException{
        MimeMessage eMailMessage= mailSender.createMimeMessage();
        MimeMessageHelper eMailMessageHelper=new MimeMessageHelper(eMailMessage,true);
        eMailMessageHelper.setTo(destinationEmail);
        eMailMessageHelper.setSubject(subject);

        final String htmlContent= this.htmlTemplateEngine.process(eMailTemplate,ctx);
        eMailMessageHelper.setText(htmlContent,true);

        mailSender.send(eMailMessage);
    }
}
