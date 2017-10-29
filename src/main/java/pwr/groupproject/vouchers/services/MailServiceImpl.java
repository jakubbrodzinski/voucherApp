package pwr.groupproject.vouchers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pwr.groupproject.vouchers.bean.model.User;
import pwr.groupproject.vouchers.bean.model.Voucher;
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
    public void sendToken(String activationLink, UserCompany company) throws MessagingException {
        final Context ctx = new Context();
        ctx.setVariable("link", activationLink);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(company.getUserName());
        helper.setSubject("Activate your account in Voucher app!");

        final String htmlContent = this.htmlTemplateEngine.process("html/tokenTemplate", ctx);
        helper.setText(htmlContent, true);
        mailSender.send(message);
    }

    @Override
    public void sendVoucher(Voucher voucher, User user) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(user.geteMail());
        helper.setSubject("subject");
        helper.setText(voucher.getCodes().iterator().next().getVoucherCode());
        mailSender.send(message);
    }

    @Override
    public void sendTest(String email, String testLink, String testText) throws MessagingException {
        final Context ctx = new Context();
        ctx.setVariable("link", testLink);
        ctx.setVariable("text", testText);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(email);
        helper.setSubject("subject");

        final String htmlContent = this.htmlTemplateEngine.process("html/simpletemplate", ctx);
        helper.setText(htmlContent, true);
        mailSender.send(message);
    }
}
