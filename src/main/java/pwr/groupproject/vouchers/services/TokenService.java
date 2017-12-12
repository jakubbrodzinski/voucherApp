package pwr.groupproject.vouchers.services;

import pwr.groupproject.vouchers.bean.exceptions.VerificationTokenExpired;
import pwr.groupproject.vouchers.bean.exceptions.WrongTokenException;
import pwr.groupproject.vouchers.bean.model.User;
import pwr.groupproject.vouchers.bean.model.security.PasswordResetToken;
import pwr.groupproject.vouchers.bean.model.security.UserCompany;
import pwr.groupproject.vouchers.bean.model.security.VerificationToken;

public interface TokenService {
    void activateAccount(String activationToken) throws VerificationTokenExpired, WrongTokenException;

    VerificationToken generateNewActivationToken(UserCompany userCompany);

    PasswordResetToken generateNewPasswordResetToken(UserCompany userCompany);

    PasswordResetToken validatePasswordResetToken(String passwordResetToken) throws WrongTokenException;

    void confirmResetingPassword(String passwordResetToken);

    UserCompany getUserCompanyByPasswordResetToken(String passwordResetToken);

    UserCompany getUserCompanyByVerificationToken(String verificationToken);

    void deleteAccountsTokens(int userCompanyId);

}
