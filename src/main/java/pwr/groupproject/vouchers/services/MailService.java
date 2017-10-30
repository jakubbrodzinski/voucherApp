package pwr.groupproject.vouchers.services;

import pwr.groupproject.vouchers.bean.model.User;
import pwr.groupproject.vouchers.bean.model.VoucherCode;
import pwr.groupproject.vouchers.bean.model.security.UserCompany;

import javax.mail.MessagingException;
import java.util.Date;

public interface MailService {
    boolean sendVerificationTokenEmail(String activationLink, Date expirationDate, String userName);
    boolean sendPasswordResetEmail(String passwordResetLink, String userName);
    boolean sendVoucherCodeEmail(VoucherCode voucher, User user);

    void sendTest(String email, String testLink, String testText) throws MessagingException;
}
