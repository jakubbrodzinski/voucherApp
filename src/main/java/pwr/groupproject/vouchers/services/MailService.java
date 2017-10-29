package pwr.groupproject.vouchers.services;

import pwr.groupproject.vouchers.bean.model.User;
import pwr.groupproject.vouchers.bean.model.Voucher;
import pwr.groupproject.vouchers.bean.model.security.UserCompany;
import pwr.groupproject.vouchers.bean.model.security.VerificationToken;

import javax.mail.MessagingException;

public interface MailService {
    void sendToken(String activationLink, UserCompany company) throws MessagingException;

    void sendVoucher(Voucher voucher, User user) throws MessagingException;

    void sendTest(String email, String testLink, String testText) throws MessagingException;
}
