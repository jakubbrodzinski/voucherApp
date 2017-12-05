package pwr.groupproject.vouchers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.webflow.execution.RequestContext;
import pwr.groupproject.vouchers.bean.dto.AnswerDto;
import pwr.groupproject.vouchers.bean.dto.ClosedQuestionDto;
import pwr.groupproject.vouchers.bean.dto.QuestionDto;
import pwr.groupproject.vouchers.bean.dto.SurveyDto;
import pwr.groupproject.vouchers.bean.enums.TokenStatus;
import pwr.groupproject.vouchers.bean.exceptions.NoAvaibleVouchersException;
import pwr.groupproject.vouchers.bean.form.AnsweredSurveyForm;
import pwr.groupproject.vouchers.bean.form.ResetPasswordForm;
import pwr.groupproject.vouchers.bean.model.*;
import pwr.groupproject.vouchers.bean.model.enums.DiscountType;
import pwr.groupproject.vouchers.bean.model.enums.QuestionType;

import pwr.groupproject.vouchers.bean.model.security.UserCompany;
import pwr.groupproject.vouchers.services.CompanySurveyService;
import pwr.groupproject.vouchers.services.MailService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class DefaultController {
    private final MailService mailService;
    private final CompanySurveyService companySurveyService;

    @Autowired
    public DefaultController(MailService mailService, CompanySurveyService companySurveyService) {
        this.mailService = mailService;
        this.companySurveyService = companySurveyService;
    }

    @RequestMapping("/testmail")
    public String testmail() {
        /*Survey survey=new Survey();
        survey.setSurveyName("SURV_NAME");
        VoucherCode voucherCode = new VoucherCode();
        Voucher voucher=new Voucher();
        voucher.setDiscountAmount(50);
        voucher.setDiscountType(DiscountType.PERCENTEGE);
        voucher.setStartDate(new Date());
        voucher.setEndDate(new Date());
        voucher.setSurvey(survey);
        voucherCode.setVoucher(voucher);
        voucherCode.setVoucherCode("codecodecode");
        mailService.sendVoucherCodeEmail(voucherCode, "jakubby@gmail.com");
        voucher.setDiscountType(DiscountType.OTHER);
        mailService.sendVoucherCodeEmail(voucherCode, "jakubby@gmail.com");
        voucher.setDiscountType(DiscountType.SUM);
        mailService.sendVoucherCodeEmail(voucherCode, "jakubby@gmail.com");*/
        mailService.sendPasswordResetEmail("token", "jakubby@gmail.com");
        mailService.sendVerificationTokenEmail("token", new Date(), "jakubby@gmail.com");
        return "index";
    }

    @RequestMapping(value = "/xyz", method = RequestMethod.GET)
    public String hello(Model model) {
        ResetPasswordForm form = new ResetPasswordForm();
        form.setResetPasswordToken("token");
        model.addAttribute("resetPasswordForm", form);
        model.addAttribute("tokenStatus", TokenStatus.OK);
        return "/token/reset_password.html";
    }

    /*@RequestMapping(value = "/testJSON", produces = "application/json")
    @ResponseBody
    public SurveyDto testJSON() {
        Survey survey = companySurveyService.getSurveyById(1);
        SurveyDto surveyWrapper = new SurveyDto();
        Collection<QuestionDto> questionWrappers = new ArrayList<>();
        for (Question question : survey.getQuestions()) {
            if (question.getQuestionType() == QuestionType.SINGLE_CHOICE || question.getQuestionType() == QuestionType.MULTIPLE_CHOICE) {
                ClosedQuestionDto closedQuestionWrapper = new ClosedQuestionDto();
                closedQuestionWrapper.setPossibleAnswers(question.getPossibleAnswers());
                closedQuestionWrapper.setQuestionBody(question.getQuestionBody());
                closedQuestionWrapper.setQuestionType(question.getQuestionType());
                questionWrappers.add(closedQuestionWrapper);
            } else {
                QuestionDto questionWrapper = new QuestionDto();
                questionWrapper.setQuestionBody(question.getQuestionBody());
                questionWrapper.setQuestionType(question.getQuestionType());
                questionWrappers.add(questionWrapper);
            }
        }
        surveyWrapper.setQuestions(questionWrappers);
        surveyWrapper.setSurveyName(survey.getSurveyName());
        return surveyWrapper;
    }*/

    /*@RequestMapping(value = "/testJSON2", produces = "application/json")
    @ResponseBody
    public SurveyDto testJSON2() {
        SurveyDto surveyDto = new SurveyDto();
        surveyDto.setSurveyName("name");
        QuestionDto questionDto = new QuestionDto();
        questionDto.setQuestionBody("xyz?");
        questionDto.setQuestionType(QuestionType.RANGED);
        surveyDto.getQuestions().add(questionDto);
        return surveyDto;
    }*/

    /*@RequestMapping(value = "/test3", method = RequestMethod.GET)
    @ResponseBody
    public String test3() {
        SurveyDto surveyDto = new SurveyDto();
        surveyDto.setSurveyName("name");
        QuestionDto questionDto = new QuestionDto();
        questionDto.setQuestionBody("xyz?");
        questionDto.setQuestionType(QuestionType.RANGED);
        surveyDto.getQuestions().add(questionDto);
        UserCompany userCompany = new UserCompany();
        userCompany.setCompany(new Company());
        userCompany.getCompany().setId(1);
        companySurveyService.addSurvey(surveyDto, userCompany);
        return "OK";
    }*/

    @RequestMapping(value = "/test4", method = RequestMethod.GET)
    @ResponseBody
    public String test4() {
        SurveyDto surveyDto = new SurveyDto();
        surveyDto.setSurveyName("name");
        ClosedQuestionDto questionDto = new ClosedQuestionDto();
        questionDto.setQuestionBody("xyz?");
        questionDto.setQuestionType(QuestionType.SINGLE_CHOICE);
        questionDto.getPossibleAnswers().setPossibleAnswerA("A");
        questionDto.getPossibleAnswers().setPossibleAnswerB("B");
        questionDto.getPossibleAnswers().setPossibleAnswerC("C");
        questionDto.getPossibleAnswers().setPossibleAnswerD("D");
        surveyDto.getQuestions().add(questionDto);
        UserCompany userCompany = new UserCompany();
        userCompany.setCompany(new Company());
        userCompany.getCompany().setId(1);
        companySurveyService.addSurvey(surveyDto, userCompany);
        return "OK";
    }

    @RequestMapping(value = "/abc/abc/test5", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasRole('COMPANY')")
    public String test5() {
        companySurveyService.deleteVoucherCode(4);
        return "OK";
    }

    @RequestMapping(value = "/testVoucher", method = RequestMethod.GET)
    public String testVoucher() {
        return "/user/receive_voucher";
    }


    @RequestMapping(value = "/landingVoucher", method = RequestMethod.GET)
    public String landVoucher() {
        return "/user/receive_finished_landing";
    }

    @RequestMapping(value = "testSurvey", method = RequestMethod.GET)
    public String testSurvey(@RequestParam(name = "surveyId") Integer surveyId, Model model) {
        model.addAttribute("surveyId", surveyId);
        Survey survey = companySurveyService.getSurveyById(surveyId);
        Collection<Question> questions = survey.getQuestions();
        model.addAttribute("surveyName", survey.getSurveyName());
        model.addAttribute("surveyCompany", survey.getCompany().getCompanyName());
        model.addAttribute("questions", questions);

        AnsweredSurveyForm answeredSurveyForm = new AnsweredSurveyForm();
        AnswerDto[] answers = new AnswerDto[survey.getQuestions().size()];
        for(int i = 0, size = survey.getQuestions().size(); i < size; i++) {
            AnswerDto answerDto = new AnswerDto();
            answerDto.setAnswerBody("");
            answers[i] = answerDto;
        }
        answeredSurveyForm.setAnswers(answers);
        model.addAttribute("answeredSurveyForm", answeredSurveyForm);

        return "/user/survey.html";
    }

    @RequestMapping(value = "testSurvey", method = RequestMethod.POST)
    public String sendSurvey(@ModelAttribute AnsweredSurveyForm answeredSurveyForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            System.out.println(":BLADS");
        }
        System.out.println(answeredSurveyForm.getAnswers()[0].getAnswerBody());
        return "/index.html";
    }

    public int testBlock(@RequestParam(name = "surveyId") Integer surveyId, RequestContext requestContext) {
        VoucherCodeDate voucherCodeDate;
        HttpSession httpSession=((HttpServletRequest)requestContext.getExternalContext().getNativeRequest()).getSession(true);
        Object vCodeId=httpSession.getAttribute("vCode");
        try {
            if(vCodeId!=null){
                companySurveyService.unBlockVoucherCode((Integer)vCodeId);
            }
            voucherCodeDate = companySurveyService.blockVoucherCodeForSurvey(surveyId);
            httpSession.setAttribute("vCode", voucherCodeDate.getId());
        } catch (NoAvaibleVouchersException e) {
            return 300;
        }
        return 200;
    }


}
