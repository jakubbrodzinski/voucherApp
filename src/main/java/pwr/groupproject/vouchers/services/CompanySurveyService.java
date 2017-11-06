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

    @PreAuthorize("hasRole('COMPANY')")
    void addSurvey(Survey survey);
    @PreAuthorize("hasRole('COMPANY')")
    void addVoucher(Voucher voucher);
    @PreAuthorize("hasRole('COMPANY')")
    void addVoucherCode(VoucherCode voucherCode,int voucherId);
    @PreAuthorize("hasRole('COMPANY')")
    void createCompany(Company company);

    @PreAuthorize("hasRole('COMPANY')")
    void deleteSurvey(int surveyId);
    @PreAuthorize("hasRole('COMPANY')")
    void deleteVoucher(int voucherId);
    @PreAuthorize("hasRole('COMPANY')")
    void deleteVoucherCode(int voucherCodeId);
    @PreAuthorize("hasRole('COMPANY')")
    void deleteCompany(int companyId);

    @PreAuthorize("hasRole('COMPANY')")
    Collection<AnsweredSurvey> getAllAnsweredSurveys(int surveyId);

    @PreAuthorize("hasRole('COMPANY')")
    AnsweredSurvey getResultDetails(int answeredSurveyId);

    Collection<Survey> getAllActiveSurveys(int companyId);
    Collection<Company> getAllActiveCompanies();

    void unBlockAllBlockedVouchersForLongerThan(int hours, int minutes);
}
