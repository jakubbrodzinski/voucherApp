package pwr.groupproject.vouchers.services;

import pwr.groupproject.vouchers.bean.model.User;
import pwr.groupproject.vouchers.bean.model.VoucherCode;

import javax.annotation.security.PermitAll;
import java.util.Date;

@PermitAll
public interface MailService {
    boolean sendVerificationTokenEmail(String activationToken, Date expirationDate, String userName);
    boolean sendPasswordResetEmail(String passwordResetToken, String userName);
    boolean sendVoucherCodeEmail(VoucherCode voucher, User user);
}
