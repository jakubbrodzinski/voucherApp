package pwr.groupproject.vouchers.services;

import pwr.groupproject.vouchers.bean.model.AnsweredSurvey;
import pwr.groupproject.vouchers.bean.model.Survey;
import pwr.groupproject.vouchers.bean.model.Voucher;
import pwr.groupproject.vouchers.bean.model.VoucherCode;

import java.util.Collection;

public class CompanySurveyServiceImpl implements CompanySurveyService {
    @Override
    public Survey getSurveyById(int surveyId) {
        return null;
    }

    @Override
    public void addAnsweredSurvey(AnsweredSurvey answeredSurvey) {

    }

    @Override
    public VoucherCode getAvaibleVoucherCode(int voucherId) {
        return null;
    }

    @Override
    public void addSurvey(Survey survey) {

    }

    @Override
    public void addVoucher(Voucher voucher) {

    }

    @Override
    public void addVoucherCode(VoucherCode voucherCode, int voucherId) {

    }

    @Override
    public Collection<AnsweredSurvey> getAllAnsweredSurveys(int surveyId) {
        return null;
    }

    @Override
    public Collection<AnsweredSurvey> getAllAnsweredSurveys(Survey survey) {
        return null;
    }

    @Override
    public AnsweredSurvey getResultDetails(int answeredSurveyId) {
        return null;
    }
}
