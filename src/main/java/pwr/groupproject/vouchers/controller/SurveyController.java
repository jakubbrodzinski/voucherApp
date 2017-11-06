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

import java.util.Collection;

@Controller
@RequestMapping(SurveyController.ROOT_MAPPING)
public class SurveyController {

    public static final String ROOT_MAPPING = "/";

    @Autowired
    private CompanySurveyService companySurveyService;

    @RequestMapping(value = "/get_company_list")
    public String companyListPage(Model model){
        Collection<Company> companies = companySurveyService.getAllActiveCompanies();
        model.addAttribute("companies",companies);
        return "/user/user_survey_chooseCompany.html";
    }
    @RequestMapping(value = "/get_surveys_from_company")
    public String voucherListPage(@RequestParam(name = "companyId", required = true) Integer companyId, Model model){
        Collection<Survey> surveys = companySurveyService.getAllActiveSurveys(companyId);
        model.addAttribute("surveys",surveys);
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
