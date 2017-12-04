package pwr.groupproject.vouchers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pwr.groupproject.vouchers.bean.dto.*;
import org.springframework.webflow.execution.RequestContext;
import pwr.groupproject.vouchers.bean.exceptions.NoAvaibleVouchersException;
import pwr.groupproject.vouchers.bean.exceptions.WrongCompanyIdException;
import pwr.groupproject.vouchers.bean.exceptions.WrongSurveyIdException;
import pwr.groupproject.vouchers.bean.form.AnsweredSurveyForm;
import pwr.groupproject.vouchers.bean.form.VoucherForm;
import pwr.groupproject.vouchers.bean.model.*;
import pwr.groupproject.vouchers.bean.model.security.UserCompany;
import pwr.groupproject.vouchers.dao.CompanySurveyDao;
import pwr.groupproject.vouchers.dao.VoucherDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.*;

@Service
@Transactional
public class CompanySurveyServiceImpl implements CompanySurveyService {
    private final CompanySurveyDao companySurveyDao;
    private final VoucherDao voucherDao;
    private final MailService mailService;

    @Autowired
    public CompanySurveyServiceImpl(CompanySurveyDao companySurveyDao, VoucherDao voucherDao,MailService mailService) {
        this.companySurveyDao = companySurveyDao;
        this.voucherDao = voucherDao;
        this.mailService=mailService;
    }

    @Override
    public Survey getSurveyById(int surveyId) {
        return companySurveyDao.getSurveyById(surveyId);
    }

    @Override
    public Survey getSurveyByIdWithQuestion(int surveyId) {
        return companySurveyDao.getSurveyWithQuestions(surveyId);
    }

    @Override
    public Survey checkIfSurveyExists(int surveyId, @AuthenticationPrincipal UserCompany userCompany) throws WrongSurveyIdException {
        Survey survey = companySurveyDao.getSurveyById(surveyId);
        if (survey.getCompany().getId() == userCompany.getCompany().getId())
            return survey;
        else
            throw new WrongSurveyIdException();
    }

    @Override
    public void addAnsweredSurvey(AnsweredSurveyForm answeredSurveyForm, int surveyId) {
        AnsweredSurvey answeredSurvey = new AnsweredSurvey();
        Survey survey = companySurveyDao.getSurveyById(surveyId);
        answeredSurvey.setSurvey(survey);

        List<Answer> answers = new ArrayList<>(answeredSurveyForm.getAnswers().length);
        List<Question> questions = (List<Question>) survey.getQuestions();
        AnswerDto[] answerDtos = answeredSurveyForm.getAnswers();
        for (int i = 0, size = answeredSurveyForm.getAnswers().length; i < size; i++) {
            Answer answer = new Answer();
            answer.setAnswer(answerDtos[i].getAnswerBody());
            answer.setAnsweredSurvey(answeredSurvey);
            answer.setQuestion(questions.get(i));

            answers.add(answer);
        }
        answeredSurvey.setAnswersList(answers);

        User user = new User();
        user.setCountry(answeredSurveyForm.getCountry());
        user.setAge(answeredSurveyForm.getAge());

        answeredSurvey.setUser(user);

        answeredSurvey.setDate(new Date());
        companySurveyDao.addAnsweredSurvey(answeredSurvey);
    }

    @Override
    public VoucherCodeDate blockVoucherCodeForSurvey(int surveyId) throws NoAvaibleVouchersException {
        Set<VoucherCode> voucherCodes = companySurveyDao.getSurveyById(surveyId).getVoucher().getCodes();
        VoucherCode voucherCode = voucherCodes.stream().filter(code -> code.getAmmountOfUses() > 0).findFirst().orElseThrow(NoAvaibleVouchersException::new);
        voucherCode.setAmmountOfUses(voucherCode.getAmmountOfUses() - 1);
        voucherCode.setAmmountofBlocked(voucherCode.getAmmountofBlocked() + 1);

        VoucherCodeDate voucherCodeDate = new VoucherCodeDate();
        voucherCodeDate.setUseDate(new Date());
        voucherCodeDate.setVoucherCode(voucherCode);
        voucherDao.addVoucherCodeDate(voucherCodeDate);

        return voucherCodeDate;
    }

    @Override
    public void unBlockVoucherCode(int voucherCodeDateId) {
        VoucherCodeDate voucherCodeDate = voucherDao.getVoucherCodeDateById(voucherCodeDateId);

        VoucherCode voucherCode = voucherCodeDate.getVoucherCode();
        voucherCode.setAmmountOfUses(voucherCode.getAmmountOfUses() + 1);
        voucherCode.setAmmountofBlocked(voucherCode.getAmmountofBlocked() - 1);
        voucherDao.updateVoucherCode(voucherCode);

        deleteVoucherCodeDate(voucherCodeDateId);
    }

    @Override
    public VoucherCodeDate getVoucherCodeDateById(int id) {
        return voucherDao.getVoucherCodeDateById(id);
    }

    @Override
    public VoucherCode deployVoucherCode(int voucherCodeDateId) {
        VoucherCode voucherCode = getVoucherCodeDateById(voucherCodeDateId).getVoucherCode();
        deleteVoucherCodeDate(voucherCodeDateId);
        return voucherCode;
    }

    @Override
    public void deleteVoucherCodeDate(int voucherCodeDateId) {
        VoucherCodeDate v = voucherDao.getVoucherCodeDateById(voucherCodeDateId);
        voucherDao.deleteVoucherCodeDate(v);
    }

    @Override
    public boolean validateSurveyIdWithVoucherCode(Integer surveyId, RequestContext requestContext) {
        VoucherCodeDate voucherCodeDate;
        HttpSession httpSession = ((HttpServletRequest) requestContext.getExternalContext().getNativeRequest()).getSession(true);
        Object vCodeId = httpSession.getAttribute("vCode");
        try {
            if (vCodeId != null) {
                unBlockVoucherCode((Integer) vCodeId);
            }
            voucherCodeDate = blockVoucherCodeForSurvey(surveyId);
            httpSession.setAttribute("vCode", voucherCodeDate.getId());
        } catch (NoAvaibleVouchersException e) {
            return false;
        }
        return true;
    }

    //TO-DO
    @Override
    public VoucherCodeDto confirmAnsweringSurvey(Integer surveyId, AnsweredSurveyForm answeredSurveyForm, RequestContext requestContext) {
        HttpSession httpSession = ((HttpServletRequest) requestContext.getExternalContext().getNativeRequest()).getSession(true);
        Integer vCodeId = (Integer) httpSession.getAttribute("vCode");
        httpSession.setAttribute("vCode", null);
        VoucherCode voucherCode=deployVoucherCode(vCodeId);
        mailService.sendVoucherCodeEmail(voucherCode,answeredSurveyForm.getEmail());
        return new VoucherCodeDto(voucherCode);
    }

    @Override
    public void addSurvey(SurveyDto surveyDto, UserCompany userCcompany) {
        Company company = companySurveyDao.getCompanyById(userCcompany.getCompany().getId());
        Survey newSurvey = new Survey();
        newSurvey.setCompany(company);
        company.getCompanysSurveys().add(newSurvey);
        newSurvey.setCreationDate(new Date());
        newSurvey.setSurveyName(surveyDto.getSurveyName());
        for (QuestionDto questionDto : surveyDto.getQuestions()) {
            Question question = new Question();
            question.setSurvey(newSurvey);
            question.setQuestionBody(questionDto.getQuestionBody());
            question.setQuestionType(questionDto.getQuestionType());
            if (questionDto instanceof ClosedQuestionDto) {
                PossibleAnswers possibleAnswers = new PossibleAnswers();
                question.setPossibleAnswers(possibleAnswers);

                PossibleAnswers possibleAnswersInput = ((ClosedQuestionDto) questionDto).getPossibleAnswers();
                possibleAnswers.setPossibleAnswerA(possibleAnswersInput.getPossibleAnswerA());
                possibleAnswers.setPossibleAnswerB(possibleAnswersInput.getPossibleAnswerB());
                possibleAnswers.setPossibleAnswerC(possibleAnswersInput.getPossibleAnswerC());
                possibleAnswers.setPossibleAnswerD(possibleAnswersInput.getPossibleAnswerD());
            }
            newSurvey.getQuestions().add(question);
        }
        companySurveyDao.addSurvey(newSurvey);
    }

    @Override
    public void addVoucher(Voucher voucher, int surveyId) {
        Survey survey = companySurveyDao.getSurveyById(surveyId);
        survey.setVoucher(voucher);
        voucher.setSurvey(survey);
        voucherDao.addVoucher(voucher);
    }

    @Override
    public void addVoucherCode(VoucherCode voucherCode, int voucherId) {
        Voucher voucher = voucherDao.getVoucherById(voucherId);
        voucherCode.setVoucher(voucher);
        voucher.getCodes().add(voucherCode);
        voucherDao.updateVoucher(voucher);
    }

    @Override
    public void createCompany(Company company) {
        companySurveyDao.addCompany(company);
    }

    @Override
    public void deleteSurvey(int surveyId) {
        Survey survey = companySurveyDao.getSurveyById(surveyId);
        companySurveyDao.deleteSurvey(survey);
    }

    @Override
    public void deleteVoucher(int voucherId) {
        Voucher voucher = voucherDao.getVoucherById(voucherId);
        voucherDao.deleteVoucher(voucher);
    }

    @Override
    public void deleteVoucherCode(int voucherCodeId) {
        VoucherCode voucherCode = voucherDao.getVoucherCode(voucherCodeId);
        voucherDao.deleteVoucherCode(voucherCode);
    }

    @Override
    public void deleteCompany(int companyId) {
        Company company = companySurveyDao.getCompanyById(companyId);
        companySurveyDao.deleteCompany(company);
    }

    @Override
    public Voucher updateVoucher(Voucher voucher, VoucherForm voucherForm) {
        voucher.setDiscountType(voucherForm.getDiscountType());
        voucher.setDiscountAmount(voucherForm.getDiscountAmount());
        voucher.setVoucherDescription(voucherForm.getVoucherDescription());
        voucher.setStartDate(voucherForm.getStartDate());
        voucher.setEndDate(voucherForm.getEndDate());
        return voucherDao.updateVoucher(voucher);
    }

    @Override
    public VoucherCode updateVoucherCode(VoucherCode voucherCode) {
        return voucherDao.updateVoucherCode(voucherCode);
    }

    @Override
    public VoucherCode getVoucherCodeById(int voucherCodeId) {
        return voucherDao.getVoucherCode(voucherCodeId);
    }

    @Override
    public Company updateCompany(Company company) {
        return companySurveyDao.updateCompany(company);
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
    public Company getCompanyWithSurveys(UserCompany userCompany) {
        return companySurveyDao.getCompanyWithSurveys(getUsersCompany(userCompany));
    }

    @Override
    public Company getUsersCompany(UserCompany userCompany) {
        return companySurveyDao.getUsersCompany(userCompany.getId());
    }


    @Override
    public Company getCompanyWithSurveysAndQuestions(Company company) {
        return companySurveyDao.getCompanyWithSurveysAndQuestions(company.getId());
    }

    @Override
    public Collection<Survey> getAllActiveSurveys(int companyId) throws WrongCompanyIdException {
        unBlockAllBlockedVouchersForLongerThan(0, 20);
        try {
            return companySurveyDao.getAvailableSurveys(companyId);
        } catch (IllegalArgumentException e) {
            throw new WrongCompanyIdException();
        }
    }

    @Override
    public Collection<Company> getAllActiveCompanies() {
        return companySurveyDao.getAllActiveCompanies();
    }

    @Override
    public void unBlockAllBlockedVouchersForLongerThan(int hours, int minutes) {
        voucherDao.unBlockAllBlockedVouchersForLongerThan(hours, minutes);
    }
}
