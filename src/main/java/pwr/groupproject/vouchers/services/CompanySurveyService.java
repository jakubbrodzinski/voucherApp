package pwr.groupproject.vouchers.services;

import org.springframework.security.access.prepost.PreAuthorize;
import pwr.groupproject.vouchers.bean.exceptions.NoAvaibleVouchersException;
import pwr.groupproject.vouchers.bean.exceptions.WrongSurveyIdException;
import pwr.groupproject.vouchers.bean.form.VoucherForm;
import pwr.groupproject.vouchers.bean.model.*;
import pwr.groupproject.vouchers.bean.dto.SurveyDto;
import pwr.groupproject.vouchers.bean.model.security.UserCompany;

import java.util.Collection;

public interface CompanySurveyService {
    Survey getSurveyById(int surveyId);
    @PreAuthorize("hasRole('COMPANY')")
    Survey checkIfSurveyExists(int surveyId, UserCompany userCompany) throws WrongSurveyIdException;
    void addAnsweredSurvey(AnsweredSurvey answeredSurvey);
    VoucherCode getVoucherCodeForSurvey(int surveyId) throws NoAvaibleVouchersException;
    void unBlockVoucherCode(int voucherCodeId);

    @PreAuthorize("hasRole('COMPANY')")
    void addSurvey(SurveyDto surveyDto, UserCompany userCompany );
    @PreAuthorize("hasRole('COMPANY')")
    void addVoucher(Voucher voucher, int surveyId);
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
    Voucher  updateVoucher(Voucher voucher,VoucherForm voucherForm);
    @PreAuthorize("hasRole('COMPANY')")
    VoucherCode updateVoucherCode(VoucherCode voucherCode);
    @PreAuthorize("hasRole('COMPANY')")
    VoucherCode getVoucherCodeById(int voucherCodeId);
    @PreAuthorize("hasRole('COMPANY')")
    Company updateCompany(Company company);

    @PreAuthorize("hasRole('COMPANY')")
    Collection<AnsweredSurvey> getAllAnsweredSurveys(int surveyId);

    @PreAuthorize("hasRole('COMPANY')")
    AnsweredSurvey getResultDetails(int answeredSurveyId);

    @PreAuthorize("hasRole('COMPANY')")
    Company getUsersCompany(UserCompany userCompany);
    Company getCompanyWithSurveys(UserCompany userCompany);
    Company getCompanyWithSurveysAndQuestions(Company company);
    Collection<Survey> getAllActiveSurveys(int companyId);
    Collection<Company> getAllActiveCompanies();

    void unBlockAllBlockedVouchersForLongerThan(int hours, int minutes);

}
