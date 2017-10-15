package pwr.groupproject.vouchers.services;

import pwr.groupproject.vouchers.bean.exceptions.VerificationTokenExpired;

public interface TokenService {
    void activateAccount(String activationToken) throws VerificationTokenExpired;
}
