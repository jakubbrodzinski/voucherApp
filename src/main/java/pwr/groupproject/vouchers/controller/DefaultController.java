package pwr.groupproject.vouchers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pwr.groupproject.vouchers.bean.dto.ClosedQuestionDto;
import pwr.groupproject.vouchers.bean.dto.QuestionDto;
import pwr.groupproject.vouchers.bean.dto.SurveyDto;
import pwr.groupproject.vouchers.bean.enums.TokenStatus;
import pwr.groupproject.vouchers.bean.form.ResetPasswordForm;
import pwr.groupproject.vouchers.bean.model.*;
import pwr.groupproject.vouchers.bean.model.enums.QuestionType;

import pwr.groupproject.vouchers.bean.model.security.UserCompany;
import pwr.groupproject.vouchers.services.CompanySurveyService;
import pwr.groupproject.vouchers.services.MailService;

import javax.annotation.security.PermitAll;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Controller
public class DefaultController {

    @Autowired
    private MailService mailService;

    @Autowired
    private CompanySurveyService companySurveyService;

    @RequestMapping("/testmail")
    public String testmail() {
        VoucherCode voucherCode=new VoucherCode();
        voucherCode.setVoucherCode("codecodecode");
        User user=new User();
        user.seteMail("jakubby@gmail.com");
        mailService.sendVoucherCodeEmail(voucherCode,user);
        mailService.sendPasswordResetEmail("token","jakubby@gmail.com");
        mailService.sendVerificationTokenEmail("token",new Date(),"jakubby@gmail.com");
        return "index";
    }

    @RequestMapping(value = "/xyz",method = RequestMethod.GET)
    public String hello(Model model){
        ResetPasswordForm form=new ResetPasswordForm();
        form.setResetPasswordToken("token");
        model.addAttribute("resetPasswordForm",form);
        model.addAttribute("tokenStatus", TokenStatus.OK);
        return "/token/reset_password.html";
    }

    @RequestMapping(value = "/testJSON", produces = "application/json")
    @ResponseBody
    public SurveyDto testJSON() {
        Survey survey = companySurveyService.getSurveyById(1);
        SurveyDto surveyWrapper = new SurveyDto();
        Collection<QuestionDto> questionWrappers = new ArrayList<>();
        for(Question question : survey.getQuestions()) {
            if(question.getQuestionType() == QuestionType.SINGLE_CHOICE || question.getQuestionType() == QuestionType.MULTIPLE_CHOICE) {
                ClosedQuestionDto closedQuestionWrapper = new ClosedQuestionDto();
                closedQuestionWrapper.setPossibleAnswers(question.getPossibleAnswers());
                closedQuestionWrapper.setQuestionBody(question.getQuestionBody());
                closedQuestionWrapper.setQuestionType(question.getQuestionType());
                questionWrappers.add(closedQuestionWrapper);
            }
            else {
                QuestionDto questionWrapper = new QuestionDto();
                questionWrapper.setQuestionBody(question.getQuestionBody());
                questionWrapper.setQuestionType(question.getQuestionType());
                questionWrappers.add(questionWrapper);
            }
        }
        surveyWrapper.setQuestions(questionWrappers);
        surveyWrapper.setSurveyName(survey.getSurveyName());
        return surveyWrapper;
    }

    @RequestMapping(value = "/testJSON2", produces = "application/json")
    @ResponseBody
    public SurveyDto testJSON2() {
        SurveyDto surveyDto=new SurveyDto();
        surveyDto.setSurveyName("name");
        QuestionDto questionDto=new QuestionDto();
        questionDto.setQuestionBody("xyz?");
        questionDto.setQuestionType(QuestionType.RANGED);
        surveyDto.getQuestions().add(questionDto);
        return  surveyDto;
    }

    @RequestMapping(value = "/test3", method = RequestMethod.GET)
    @ResponseBody
    public String test3() {
        SurveyDto surveyDto=new SurveyDto();
        surveyDto.setSurveyName("name");
        QuestionDto questionDto=new QuestionDto();
        questionDto.setQuestionBody("xyz?");
        questionDto.setQuestionType(QuestionType.RANGED);
        surveyDto.getQuestions().add(questionDto);
        UserCompany userCompany=new UserCompany();
        userCompany.setCompany(new Company());
        userCompany.getCompany().setId(1);
        companySurveyService.addSurvey(surveyDto,userCompany);
        return "OK";
    }

    @RequestMapping(value = "/test4", method = RequestMethod.GET)
    @ResponseBody
    public String test4() {
        SurveyDto surveyDto=new SurveyDto();
        surveyDto.setSurveyName("name");
        ClosedQuestionDto questionDto=new ClosedQuestionDto();
        questionDto.setQuestionBody("xyz?");
        questionDto.setQuestionType(QuestionType.SINGLE_CHOICE);
        questionDto.getPossibleAnswers().setPossibleAnswerA("A");
        questionDto.getPossibleAnswers().setPossibleAnswerB("B");
        questionDto.getPossibleAnswers().setPossibleAnswerC("C");
        questionDto.getPossibleAnswers().setPossibleAnswerD("D");
        surveyDto.getQuestions().add(questionDto);
        UserCompany userCompany=new UserCompany();
        userCompany.setCompany(new Company());
        userCompany.getCompany().setId(1);
        companySurveyService.addSurvey(surveyDto,userCompany);
        return "OK";
    }

    @RequestMapping(value = "/abc/abc/test5", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasRole('COMPANY')")
    public String test5() {
        companySurveyService.deleteVoucherCode(4);
        return "OK";
    }
}
