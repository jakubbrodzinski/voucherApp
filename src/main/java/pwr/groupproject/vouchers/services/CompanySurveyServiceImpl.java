package pwr.groupproject.vouchers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pwr.groupproject.vouchers.bean.exceptions.NoAvaibleVouchersException;
import pwr.groupproject.vouchers.bean.model.*;
import pwr.groupproject.vouchers.dao.CompanySurveyDao;
import pwr.groupproject.vouchers.dao.VoucherDao;

import java.util.Collection;

@Service
@Transactional
@PreAuthorize("hasRole('USER')")
public class CompanySurveyServiceImpl implements CompanySurveyService {
    @Autowired
    private CompanySurveyDao companySurveyDao;
    @Autowired
    private VoucherDao voucherDao;

    @Override
    public Survey getSurveyById(int surveyId) {
        return companySurveyDao.getSurveyById(surveyId);
    }

    @Override
    public void addAnsweredSurvey(AnsweredSurvey answeredSurvey) {
        companySurveyDao.addAnsweredSurvey(answeredSurvey);
    }

    @Override
    public VoucherCode getAvaibleVoucherCode(int voucherId) throws NoAvaibleVouchersException {
        Voucher voucher=voucherDao.getVoucherById(voucherId);
        return voucher.getCodes().stream().filter(VoucherCode::isAvaible).findFirst().orElseThrow(NoAvaibleVouchersException::new);
    }

    @Override
    public void addSurvey(Survey survey) {
        companySurveyDao.addSurvey(survey);
    }

    @Override
    public void addVoucher(Voucher voucher) {
        voucherDao.addVoucher(voucher);
    }

    @Override
    public void addVoucherCode(VoucherCode voucherCode, int voucherId) {
        Voucher voucher=voucherDao.getVoucherById(voucherId);
        voucher.getCodes().add(voucherCode);
        voucherDao.updateVoucher(voucher);
    }

    @Override
    public void deleteSurvey(int surveyId) {
        Survey survey=companySurveyDao.getSurveyById(surveyId);
        companySurveyDao.deleteSurvey(survey);
    }

    @Override
    public void deleteVoucher(int voucherId) {
        Voucher voucher=voucherDao.getVoucherById(voucherId);
        voucherDao.deleteVoucher(voucher);
    }

    @Override
    public void deleteVoucherCode(int voucherCodeId) {
        VoucherCode voucherCode=voucherDao.getVoucherCode(voucherCodeId);
        voucherDao.deleteVoucherCode(voucherCode);
    }

    @Override
    public Collection<AnsweredSurvey> getAllAnsweredSurveys(int surveyId) {
        return companySurveyDao.getAllResultsOfSurvey(surveyId);
    }

    @Override
    public AnsweredSurvey getResultDetails(int answeredSurveyId) {
        return companySurveyDao.getAnsweredSurveyById(answeredSurveyId);
    }

    @Override
    public Collection<Survey> getAllActiveSurveys(int companyId) {
        return null;
    }

}
