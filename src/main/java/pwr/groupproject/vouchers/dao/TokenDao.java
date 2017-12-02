package pwr.groupproject.vouchers.dao;

import pwr.groupproject.vouchers.bean.model.security.PasswordResetToken;
import pwr.groupproject.vouchers.bean.model.security.VerificationToken;

public interface TokenDao {
    PasswordResetToken getPasswordResetTokenByToken(String resetToken);

    PasswordResetToken getPasswordResetTokenById(int tokenId);

    void deleteResetToken(PasswordResetToken passwordResetToken);

    void addPasswordResetToken(PasswordResetToken passwordResetToken);

    VerificationToken getVerificationTokenByToken(String verificationToken);

    VerificationToken getVerificationTokenById(int tokenId);

    void deletVerificationToken(VerificationToken verificationToken);

    void addVerificationToken(VerificationToken verificationToken);

    void deleteUsersResetTokens(String userName);

    void deleteUsersVerificationTokens(String userName);
}
