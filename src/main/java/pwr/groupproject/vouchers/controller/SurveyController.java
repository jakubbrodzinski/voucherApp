package pwr.groupproject.vouchers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pwr.groupproject.vouchers.bean.model.Company;
import pwr.groupproject.vouchers.bean.model.Question;
import pwr.groupproject.vouchers.bean.model.Survey;
import pwr.groupproject.vouchers.services.CompanySurveyService;
import pwr.groupproject.vouchers.services.UserCompanyService;

import java.util.Collection;

@Controller
@RequestMapping(SurveyController.ROOT_MAPPING)
public class SurveyController {

    public static final String ROOT_MAPPING = "/";

    @Autowired
    private CompanySurveyService companySurveyService;
    @Autowired
    private UserCompanyService userCompanyService;

    @RequestMapping(value = "/get_company_list")
    public String companyListPage(Model model){
        /*Czekam kotku na implementację :*
        Collection<Company> companys = userCompanyService.getAllUsers();
        model.addAttribute("companys",companys);*/
        return "/user/user_survey_chooseCompany.html";
    }
    @RequestMapping(value = "/get_vouchers_from_company")
    public String voucherListPage(@RequestParam(name = "comapnyId", required = true) Integer companyId, Model model){
        /*Czekam kochanie na implementację :*
        Collection<Voucher> vouchers = userCompanyService.getVouchersByCompany(companyID);
        model.addAttribute("vouchers",vouchers);*/
        return "/user/user_survey_chooseVoucher.html";
    }
    @RequestMapping(value = "/get_surveys_related_to_voucher")
    public String surveyListPage(@RequestParam(name = "voucherID", required = true) Integer voucherId, Model model){
        /*Czekam pysiu na implementację :*
        Collection<Survey> surveys = userCompanyService.getSurveysByVoucher(voucherID);
        model.addAttribute("surveys",surveys);*/
        return "/user/user_survey_chooseSurvey.html";
    }

    @RequestMapping(value = "surveys", method = RequestMethod.GET)
    public String renderSurvey(@RequestParam(name = "surveyId", required = true) Integer surveyId, Model model) {
        model.addAttribute("surveyId", surveyId);
        Survey survey = companySurveyService.getSurveyById(surveyId);
        Collection<Question> questions = survey.getQuestions();
        model.addAttribute("surveyName", survey.getSurveyName());
        model.addAttribute("surveyCompany", survey.getCompany().getCompanyName());
        model.addAttribute("questions", questions);
        return "/user/survey.html";
    }

    @RequestMapping(value = "surveys", method = RequestMethod.POST)
    public String renderSurvey() {
        return new String("redirect:/");
    }
}
