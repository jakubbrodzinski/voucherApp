package pwr.groupproject.vouchers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pwr.groupproject.vouchers.bean.model.Survey;
import pwr.groupproject.vouchers.bean.model.Voucher;
import pwr.groupproject.vouchers.services.CompanySurveyService;
import pwr.groupproject.vouchers.services.UserCompanyService;

import java.security.Principal;
import java.util.Collection;

@Controller
@RequestMapping(UserCompanyController.ROOT_MAPPING)
@PreAuthorize("hasRole('USER')")
public class UserCompanyController {
    public static final String ROOT_MAPPING="/my_account";
    @Autowired
    private CompanySurveyService companySurveyService;
    @Autowired
    private UserCompanyService userCompanyService;

    @RequestMapping("/home")
    public String homePage(Model model){ return "my_account/home_page.html";
    }
    @RequestMapping("/my_profile")
    public String myProfile(Model model){
        return "my_account/my_profile";
    }
    @RequestMapping("/manage_surveys")
    public String surveysHomePage(Model model){
        return "my_account/surveys/survey_homePage";
    }
    @RequestMapping("/manage_vouchers")
    public String vouchersHomePage(Model model){
        return "my_account/vouchers/vouchers_homePage";
    }
    @RequestMapping("/statistics")
    public String statsHomePage(Model model){
        return "my_account/stats/stats_homePage";
    }
    @RequestMapping("/manage_surveys/create_new_survey")
    public String createNewSurveyPage(Model model){
        return "my_account/surveys/create_survey";
    }
    @RequestMapping("/manage_surveys/view_existing_surveys")
    public String existingSurveysPage(Principal userCompany, Model model){
        Collection<Survey> surveys = companySurveyService.getAllActiveSurveys(userCompanyService.getUserByUserName(userCompany.getName()).getId());
        model.addAttribute("surveys",surveys);
        return "my_account/surveys/existing_surveys";
    }
    @RequestMapping("/manage_surveys/view_existing_surveys/view_concrete_survey")
    public String previewSurveyPage(@RequestParam(name = "surveyId", required = true) Integer surveyId, Model model){
        Survey survey = companySurveyService.getSurveyById(surveyId);
        model.addAttribute("survey",survey);
        return "my_account/surveys/preview_survey";
    }
    @RequestMapping("/manage_vouchers/create_new_voucher")
    public String createNewVoucherPage(Model model){
        return "my_account/vouchers/add_voucher";
    }
    @RequestMapping("/manage_vouchers/view_existing_vouchers")
    public String existingVouchersPage(Principal userCompany, Model model){
        Collection<Survey> surveys = companySurveyService.getAllActiveSurveys(userCompanyService.getUserByUserName(userCompany.getName()).getId());
        model.addAttribute("surveys",surveys);
        return "my_account/vouchers/manage_vouchers";
    }
    @RequestMapping("/manage_vouchers/view_existing_vouchers/view_concrete_voucher")
    public String previewVoucherPage(@RequestParam(name = "surveyId", required = true) Integer surveyId, Model model){
        Voucher voucher = companySurveyService.getSurveyById(surveyId).getVoucher();
        System.out.println(voucher.getVoucherDescription());
        model.addAttribute("voucher",voucher);
        return "my_account/vouchers/preview_voucher.html";
    }
}

