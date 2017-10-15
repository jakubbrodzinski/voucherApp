package pwr.groupproject.vouchers.services;

import pwr.groupproject.vouchers.bean.model.AnsweredSurvey;
import pwr.groupproject.vouchers.bean.model.Survey;
import pwr.groupproject.vouchers.bean.model.Voucher;
import pwr.groupproject.vouchers.bean.model.VoucherCode;

import java.util.Collection;

public interface CompanySurveyService {
    Survey getSurveyById(int surveyId);
    void addAnsweredSurvey(AnsweredSurvey answeredSurvey);
    VoucherCode getAvaibleVoucherCode(int voucherId);

    void addSurvey(Survey survey);
    void addVoucher(Voucher voucher);
    void addVoucherCode(VoucherCode voucherCode,int voucherId);

    Collection<AnsweredSurvey> getAllAnsweredSurveys(int surveyId);
    Collection<AnsweredSurvey> getAllAnsweredSurveys(Survey survey);

    AnsweredSurvey getResultDetails(int answeredSurveyId);

}
