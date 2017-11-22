package pwr.groupproject.vouchers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pwr.groupproject.vouchers.bean.model.Company;
import pwr.groupproject.vouchers.bean.model.Question;
import pwr.groupproject.vouchers.bean.model.Survey;
import pwr.groupproject.vouchers.bean.dto.QuestionDto;
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
    UserCompanyService userCompanyService;

    @RequestMapping(value = "/companies")
    public String companyList(Model model){
        Collection<Company> companies = companySurveyService.getAllActiveCompanies();
        model.addAttribute("companies",companies);
        return "/user/chooseCompany.html";
    }
    @RequestMapping(value = "/companySurveys")
    public String voucherList(@RequestParam(name = "companyId", required = true) Integer companyId, Model model){
        /*check if company exists optional
        if(userCompanyService.getUserCompanyByCompanyId(companyId)==null){
            return "/error.html";
        }*/

        Collection<Survey> surveys = companySurveyService.getAllActiveSurveys(companyId);
        if(surveys.size()==0){
            //company does not exist or doesn't have any surveys
            return "/error.html";
        }
        model.addAttribute("surveys",surveys);
        return "/user/chooseSurvey.html";
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

}
