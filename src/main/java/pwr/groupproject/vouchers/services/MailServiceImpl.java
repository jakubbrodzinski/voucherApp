package pwr.groupproject.vouchers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
    @Value("${web.app.url}")
    private String APP_URL;
    private final JavaMailSender mailSender;
    private final TemplateEngine htmlTemplateEngine;
    private final TemplateEngine textTemplateEngine;
    private final MessageSource messageSource;

    @Autowired
    public MailServiceImpl(JavaMailSender mailSender, TemplateEngine htmlTemplateEngine, TemplateEngine textTemplateEngine, @Qualifier("mailMessageSource") MessageSource messageSource) {
        this.mailSender = mailSender;
        this.htmlTemplateEngine = htmlTemplateEngine;
        this.textTemplateEngine = textTemplateEngine;
        this.messageSource = messageSource;
    }

    @Override
    public boolean sendVerificationTokenEmail(String activationToken, Date expirationDate, String userName) {
        final Context ctx = new Context();
        ctx.setVariable("activationLink", generateActivationLink(activationToken));
        String eMailTitle = messageSource.getMessage("activate.account.email.title", null, LocaleContextHolder.getLocale());

        return this.send(userName, eMailTitle, ctx, "activateAccountEmail");
    }

    @Override
    public boolean sendPasswordResetEmail(String passwordResetLink, String userName) {
        final Context ctx = new Context();
        ctx.setVariable("resetPasswordLink", generatePasswordResetLink(passwordResetLink));
        String eMailTitle = messageSource.getMessage("reset.password.email.title", null, LocaleContextHolder.getLocale());

        return this.send(userName, eMailTitle, ctx, "resetPasswordEmail");
    }

    @Override
    public boolean sendVoucherCodeEmail(VoucherCode voucher, String email) {
        final Context ctx = new Context();
        ctx.setVariable("voucherCode", voucher.getVoucherCode());
        ctx.setVariable("voucher",voucher.getVoucher());
        ctx.setVariable("companyName", "companyName");
        String eMailTitle = messageSource.getMessage("voucher.code.email.title", null, LocaleContextHolder.getLocale());

        return this.send(email, eMailTitle, ctx, "voucherCodeEmail");
    }

    private boolean send(String destinationEmail, String subject, Context ctx, String eMailTemplate) {
        MimeMessage eMailMessage = mailSender.createMimeMessage();
        MimeMessageHelper eMailMessageHelper;
        try {
            eMailMessageHelper = new MimeMessageHelper(eMailMessage, true);
            eMailMessageHelper.setTo(destinationEmail);
            eMailMessageHelper.setSubject(subject);

            final String htmlContent = this.htmlTemplateEngine.process(eMailTemplate, ctx);
            eMailMessageHelper.setText(htmlContent, true);
            mailSender.send(eMailMessage);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String generateActivationLink(String activationToken) {
        return APP_URL + "token/activate_account?t=" + activationToken;
    }

    private String generatePasswordResetLink(String passwordResetToken) {
        return APP_URL + "token/reset_password?t=" + passwordResetToken;
    }
}
