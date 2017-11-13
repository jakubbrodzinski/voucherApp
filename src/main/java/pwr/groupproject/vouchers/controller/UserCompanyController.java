package pwr.groupproject.vouchers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pwr.groupproject.vouchers.bean.dto.SurveyDto;
import pwr.groupproject.vouchers.bean.exceptions.WrongSurveyIdException;
import pwr.groupproject.vouchers.bean.model.Company;
import pwr.groupproject.vouchers.bean.model.Survey;
import pwr.groupproject.vouchers.bean.model.Voucher;
import pwr.groupproject.vouchers.bean.model.security.UserCompany;
import pwr.groupproject.vouchers.services.CompanySurveyService;
import pwr.groupproject.vouchers.services.UserCompanyService;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Collection;

@Controller
@RequestMapping(UserCompanyController.ROOT_MAPPING)
@PreAuthorize("hasRole('USER')")
public class UserCompanyController {
    public static final String ROOT_MAPPING = "/my_account";
    @Autowired
    private CompanySurveyService companySurveyService;
    @Autowired
    private UserCompanyService userCompanyService;

    @RequestMapping("/home")
    public String homePage(Model model) {
        return "my_account/home_page.html";
    }

    @RequestMapping("/account_panel")
    public String accountPanel(Model model) {
        return "my_account/account_panel.html";
    }

    @RequestMapping(value = "/surveys",method = RequestMethod.GET)
    public String surveysHomePage(Model model,@AuthenticationPrincipal UserCompany userCompany) {
        Company companyWithSurveys=companySurveyService.getCompanyWithSurveys(userCompany.getCompany());
        model.addAttribute("surveyList",companyWithSurveys.getCompanysSurveys());
        return "my_account/surveys/surveys.html";
    }

    @RequestMapping(value="/surveys/{id}",method = RequestMethod.GET)
    public String surveyDetails(@PathVariable("id") int surveyId,Model model,@AuthenticationPrincipal UserCompany userCompany){
        try {
            Survey survey = companySurveyService.checkIfSurveyExists(surveyId, userCompany);
            model.addAttribute("survey",survey);
            return "my_account/surveys/survey_details.html";
        }catch(WrongSurveyIdException ex){
            ex.printStackTrace();
            return "/error.html"; //I have no idea how to tell spring that error occured properly :( . FIX IT PLZ!
        }
    }

    @RequestMapping(value="/surveys/{id}/vouchers",method = RequestMethod.GET)
    public String manageSurveysVoucher(@PathVariable("id") int surveyId,Model model,@AuthenticationPrincipal UserCompany userCompany){
        try {
            Survey survey = companySurveyService.checkIfSurveyExists(surveyId, userCompany);
            model.addAttribute("surveyName",survey.getSurveyName());
            model.addAttribute("voucher",survey.getVoucher());
            return "/my_account/vouchers/manage_vouchers.html";
        }catch(WrongSurveyIdException ex){
            ex.printStackTrace();
            return "/error.html";
        }
    }

    @RequestMapping(value = "/surveys/add", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public String newSurvey(@RequestParam SurveyDto surveyDto, @AuthenticationPrincipal UserCompany userCompany, HttpServletResponse httpServletResponse) {
        try {
            companySurveyService.addSurvey(surveyDto, userCompany.getCompany());
            httpServletResponse.setStatus(HttpServletResponse.SC_CREATED);
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "NOT";
        }
    }

    @RequestMapping(value = "/surveys/add",method = RequestMethod.GET)
    public String newSurvey(Model model) {
        return "my_account/surveys/create_survey.html";
    }


    @RequestMapping("/statistics")
    public String statsHomePage(Model model) {
        return "my_account/stats/stats_homePage";
    }


    @RequestMapping("/manage_vouchers/create_new_voucher")
    public String createNewVoucherPage(Model model) {
        return "my_account/vouchers/add_voucher";
    }

}


