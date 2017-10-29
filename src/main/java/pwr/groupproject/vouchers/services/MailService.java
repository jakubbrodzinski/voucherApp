package pwr.groupproject.vouchers.services;

import pwr.groupproject.vouchers.bean.model.User;
import pwr.groupproject.vouchers.bean.model.VoucherCode;
import pwr.groupproject.vouchers.bean.model.security.UserCompany;

import javax.mail.MessagingException;

public interface MailService {
    void sendVerificationTokenEmail(String activationLink, UserCompany company) throws MessagingException;
    void sendPasswordResetEmail(String passwordResetLink, UserCompany company) throws MessagingException;
    void sendVoucherCodeEmail(VoucherCode voucher, User user) throws MessagingException;

    void sendTest(String email, String testLink, String testText) throws MessagingException;
}
