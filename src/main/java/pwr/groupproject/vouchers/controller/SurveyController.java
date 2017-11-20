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

import java.util.Collection;

@Controller
@RequestMapping(SurveyController.ROOT_MAPPING)
public class SurveyController {

    public static final String ROOT_MAPPING = "/";

    @Autowired
    private CompanySurveyService companySurveyService;

    @RequestMapping(value = "/companies")
    public String companyList(Model model){
        Collection<Company> companies = companySurveyService.getAllActiveCompanies();
        model.addAttribute("companies",companies);
        return "/user/chooseCompany.html";
    }
    @RequestMapping(value = "/companySurveys")
    public String voucherList(@RequestParam(name = "companyId", required = true) Integer companyId, Model model){
        Collection<Survey> surveys = companySurveyService.getAllActiveSurveys(companyId);
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
