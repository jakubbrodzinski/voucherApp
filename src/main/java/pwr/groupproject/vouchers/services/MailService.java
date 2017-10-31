package pwr.groupproject.vouchers.services;

import pwr.groupproject.vouchers.bean.model.User;
import pwr.groupproject.vouchers.bean.model.VoucherCode;
import pwr.groupproject.vouchers.bean.model.security.UserCompany;

import javax.annotation.security.PermitAll;
import javax.mail.MessagingException;
import java.util.Date;

@PermitAll
public interface MailService {
    boolean sendVerificationTokenEmail(String activationToken, Date expirationDate, String userName);
    boolean sendPasswordResetEmail(String passwordResetToken, String userName);
    boolean sendVoucherCodeEmail(VoucherCode voucher, User user);
}
