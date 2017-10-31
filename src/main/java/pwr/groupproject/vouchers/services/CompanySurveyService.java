package pwr.groupproject.vouchers.services;

import org.springframework.security.access.prepost.PreAuthorize;
import pwr.groupproject.vouchers.bean.exceptions.NoAvaibleVouchersException;
import pwr.groupproject.vouchers.bean.model.*;

import java.util.Collection;

public interface CompanySurveyService {
    Survey getSurveyById(int surveyId);
    void addAnsweredSurvey(AnsweredSurvey answeredSurvey);
    VoucherCode getVoucherCodeForSurvey(int surveyId) throws NoAvaibleVouchersException;
    void unBlockVoucherCode(int voucherCodeId);

    @PreAuthorize("hasRole('USER')")
    void addSurvey(Survey survey);
    @PreAuthorize("hasRole('USER')")
    void addVoucher(Voucher voucher);
    @PreAuthorize("hasRole('USER')")
    void addVoucherCode(VoucherCode voucherCode,int voucherId);

    @PreAuthorize("hasRole('USER')")
    void deleteSurvey(int surveyId);
    @PreAuthorize("hasRole('USER')")
    void deleteVoucher(int voucherId);
    @PreAuthorize("hasRole('USER')")
    void deleteVoucherCode(int voucherCodeId);

    @PreAuthorize("hasRole('USER')")
    Collection<AnsweredSurvey> getAllAnsweredSurveys(int surveyId);

    @PreAuthorize("hasRole('USER')")
    AnsweredSurvey getResultDetails(int answeredSurveyId);

    Collection<Survey> getAllActiveSurveys(int companyId);
}
