package pwr.groupproject.vouchers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pwr.groupproject.vouchers.bean.exceptions.NoAvaibleVouchersException;
import pwr.groupproject.vouchers.bean.exceptions.WrongCompanyIdException;
import pwr.groupproject.vouchers.bean.model.Company;
import pwr.groupproject.vouchers.bean.model.Question;
import pwr.groupproject.vouchers.bean.model.Survey;
import pwr.groupproject.vouchers.bean.model.VoucherCodeDate;
import pwr.groupproject.vouchers.services.CompanySurveyService;
import pwr.groupproject.vouchers.services.UserCompanyService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;

@Controller
@RequestMapping(SurveyController.ROOT_MAPPING)
public class SurveyController {
    static final String ROOT_MAPPING = "/";
    private final CompanySurveyService companySurveyService;
    private final UserCompanyService userCompanyService;
    private final MessageSource messageSource;

    @Autowired
    public SurveyController(CompanySurveyService companySurveyService, UserCompanyService userCompanyService, MessageSource messageSource) {
        this.companySurveyService = companySurveyService;
        this.userCompanyService = userCompanyService;
        this.messageSource = messageSource;
    }

    @RequestMapping(value = "/companies")
    public String getActiveCompanies(Model model, @RequestParam(value = "err", required = false) Integer errCode) {
        Collection<Company> companies = companySurveyService.getAllActiveCompanies();
        model.addAttribute("companies", companies);
        if (errCode != null)
            model.addAttribute("err", messageSource.getMessage("companies.error" + errCode, null, LocaleContextHolder.getLocale()));
        return "/user/companies_list.html";
    }

    @RequestMapping(value = "/comps_surveys",method = RequestMethod.GET)
    public String voucherList(@RequestParam(name = "companyId") Integer companyId,@RequestParam(name="unblock",required = false)Integer unBlockParam, Model model, RedirectAttributes redirectAttributes,HttpServletRequest httpServletRequest) {
        if(unBlockParam!=null && unBlockParam==1){
            Integer vCodeId=(Integer) httpServletRequest.getSession(false).getAttribute("vCode");
            if(vCodeId!=null){
                companySurveyService.unBlockVoucherCode(vCodeId);
                httpServletRequest.getSession(false).setAttribute("vCode",null);
            }
        }
        Collection<Survey> surveys;
        try {
            surveys = companySurveyService.getAllActiveSurveys(companyId);
        } catch (WrongCompanyIdException e) {
            model.addAttribute("errDetails", "Company doesn't exist.");
            return "/error.html";
        }
        if (surveys.size() == 0) {
            redirectAttributes.addAttribute("err", "1");
            return "redirect:" + "/companies";
        }
        model.addAttribute("surveys", surveys);
        return "/user/choose_survey.html";
    }

    @RequestMapping(value = "fill_survey", method = RequestMethod.GET)
    public String renderSurvey(@RequestParam(name = "surveyId") Integer surveyId, Model model, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
        VoucherCodeDate voucherCodeDate;
        HttpSession httpSession=httpServletRequest.getSession(true);
        Object vCodeId=httpSession.getAttribute("vCode");
        try {
            if(vCodeId!=null){
                companySurveyService.unBlockVoucherCode((Integer)vCodeId);
            }
            voucherCodeDate = companySurveyService.blockVoucherCodeForSurvey(surveyId);
            httpSession.setAttribute("vCode", voucherCodeDate.getId());
        } catch (NoAvaibleVouchersException e) {
            redirectAttributes.addAttribute("err", "2");
            return "redirect:" + "/companies";
        }
        model.addAttribute("surveyId", surveyId);
        Survey survey = companySurveyService.getSurveyById(surveyId);
        Collection<Question> questions = survey.getQuestions();
        model.addAttribute("surveyName", survey.getSurveyName());
        model.addAttribute("surveyCompany", survey.getCompany().getCompanyName());
        model.addAttribute("questions", questions);
        return "/user/survey.html";
    }

    @RequestMapping(value = "fill_survey", method = RequestMethod.POST)
    @ResponseBody
    public String testDeployingVoucher(HttpServletRequest httpServletRequest) {
        Integer vCodeId = (Integer) httpServletRequest.getSession().getAttribute("vCode");
        httpServletRequest.getSession().setAttribute("vCode", null);
        return companySurveyService.deployVoucherCode(vCodeId).toString();
    }


}
