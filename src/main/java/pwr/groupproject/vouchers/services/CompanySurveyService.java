package pwr.groupproject.vouchers.services;

import pwr.groupproject.vouchers.bean.exceptions.NoAvaibleVouchersException;
import pwr.groupproject.vouchers.bean.model.*;

import java.util.Collection;

public interface CompanySurveyService {
    Survey getSurveyById(int surveyId);
    void addAnsweredSurvey(AnsweredSurvey answeredSurvey);
    VoucherCode getVoucherCodeForSurvey(int surveyId) throws NoAvaibleVouchersException;
    void unBlockVoucherCode(int voucherCodeId);

    void addSurvey(Survey survey);
    void addVoucher(Voucher voucher);
    void addVoucherCode(VoucherCode voucherCode,int voucherId);

    void deleteSurvey(int surveyId);
    void deleteVoucher(int voucherId);
    void deleteVoucherCode(int voucherCodeId);

    Collection<AnsweredSurvey> getAllAnsweredSurveys(int surveyId);

    AnsweredSurvey getResultDetails(int answeredSurveyId);

    Collection<Survey> getAllActiveSurveys(int companyId);
}
