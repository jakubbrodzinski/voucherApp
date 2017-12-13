package pwr.groupproject.vouchers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pwr.groupproject.vouchers.bean.exceptions.WrongCompanyIdException;
import pwr.groupproject.vouchers.bean.model.Company;
import pwr.groupproject.vouchers.bean.model.Survey;
import pwr.groupproject.vouchers.services.CompanySurveyService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@Controller
@RequestMapping(SurveyController.ROOT_MAPPING)
public class SurveyController {
    static final String ROOT_MAPPING = "/";
    private final CompanySurveyService companySurveyService;
    private final MessageSource messageSource;

    @Autowired
    public SurveyController(CompanySurveyService companySurveyService, MessageSource messageSource) {
        this.companySurveyService = companySurveyService;
        this.messageSource = messageSource;
    }

    @RequestMapping(value = "/companies")
    public String getActiveCompanies(Model model, @RequestParam(value = "err", required = false) Integer errCode, @RequestParam(name="unblock",required = false)Integer unBlockParam,HttpServletRequest httpServletRequest) {
        if(unBlockParam!=null && unBlockParam==1){
            Integer vCodeId=(Integer) httpServletRequest.getSession(false).getAttribute("vCode");
            if(vCodeId!=null){
                companySurveyService.unBlockVoucherCode(vCodeId);
                httpServletRequest.getSession(false).removeAttribute("vCode");
                //httpServletRequest.getSession(false).setAttribute("vCode",null);
            }
        }
        Collection<Company> companies = companySurveyService.getAllActiveCompanies();
        model.addAttribute("companies", companies);
        if (errCode != null)
            model.addAttribute("err", messageSource.getMessage("companies.error" + errCode, null, LocaleContextHolder.getLocale()));
        return "/user/companies_list.html";
    }

    @RequestMapping(value = "/comps_surveys",method = RequestMethod.GET)
    public String voucherList(@RequestParam(name = "companyId") Integer companyId, Model model, RedirectAttributes redirectAttributes) {
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

}
