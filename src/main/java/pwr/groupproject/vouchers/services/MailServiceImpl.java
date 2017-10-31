package pwr.groupproject.vouchers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pwr.groupproject.vouchers.bean.model.User;
import pwr.groupproject.vouchers.bean.model.VoucherCode;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

@Component
public class MailServiceImpl implements MailService {
    private final String APP_URL;

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TemplateEngine htmlTemplateEngine;
    @Autowired
    private TemplateEngine textTemplateEngine;
    @Autowired
    private Environment environment;
    @Autowired
    @Qualifier("mailMessageSource")
    private MessageSource messageSource;

    public MailServiceImpl(){
        APP_URL=environment.getProperty("web.app.url");
    }

    @Override
    public boolean sendVerificationTokenEmail(String activationToken, Date expirationDate, String userName){
        final Context ctx = new Context();
        ctx.setVariable("link", generateActivationLink(activationToken));
        String eMailTitle=messageSource.getMessage("activate.account.email.title",null, LocaleContextHolder.getLocale());

        return this.send(userName,eMailTitle,ctx,"activateAccountEmail");
    }

    @Override
    public boolean sendPasswordResetEmail(String passwordResetLink, String userName) {
        final Context ctx = new Context();
        ctx.setVariable("link", generatePasswordResetLink(passwordResetLink));
        String eMailTitle=messageSource.getMessage("reset.password.email.title",null, LocaleContextHolder.getLocale());

        return this.send(userName,eMailTitle,ctx,"resetPasswordEmail");
    }

    @Override
    public boolean sendVoucherCodeEmail(VoucherCode voucher, User user) {
        final Context ctx=new Context();
        ctx.setVariable("voucherCode",voucher.getVoucherCode());
        String eMailTitle=messageSource.getMessage("voucher.code.email.title",null, LocaleContextHolder.getLocale());

        return this.send(user.geteMail(),eMailTitle,ctx,"voucherCodeEmail");
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

    private String generateActivationLink(String activationToken){
        return APP_URL+"token/activate_account?t="+activationToken;
    }

    private String generatePasswordResetLink(String passwordResetToken){
        return APP_URL+"token/reset_password?t="+passwordResetToken;
    }
}
