package pwr.groupproject.vouchers.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pwr.groupproject.vouchers.bean.dto.CompanyDto;
import pwr.groupproject.vouchers.bean.dto.rest.SurveyDtoRest;
import pwr.groupproject.vouchers.bean.dto.SurveyVoucherDto;
import pwr.groupproject.vouchers.bean.exceptions.NoAvaibleVouchersException;
import pwr.groupproject.vouchers.bean.exceptions.WrongCompanyIdException;
import pwr.groupproject.vouchers.bean.model.Question;
import pwr.groupproject.vouchers.bean.model.Survey;
import pwr.groupproject.vouchers.bean.model.VoucherCodeDate;
import pwr.groupproject.vouchers.services.CompanySurveyService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping(RestSurveyController.ROOT_MAPPING)
public class RestSurveyController {
    static final String ROOT_MAPPING = "/api/rest";
    private final CompanySurveyService companySurveyService;

    @Autowired
    public RestSurveyController(CompanySurveyService companySurveyService) {
        this.companySurveyService = companySurveyService;
    }

    @RequestMapping(value = "/companies",produces = MediaType.APPLICATION_JSON_UTF8_VALUE,method = RequestMethod.GET)
    public Collection<CompanyDto> getAllActiveCompanies(){
        return companySurveyService.getAllActiveCompanies().stream().map(CompanyDto::new).collect(Collectors.toList());
    }

    @RequestMapping(value = "/companies/{compId}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE,method = RequestMethod.GET)
    public Collection<SurveyVoucherDto> getCompanysActiveSurveys(@PathVariable(name = "compId")Integer compId, HttpServletResponse httpServletResponse){
        try{
            Collection<Survey> activeSurveys=companySurveyService.getAllActiveSurveys(compId);
            return activeSurveys.stream().map(SurveyVoucherDto::new).collect(Collectors.toList());
        }catch(WrongCompanyIdException e){
            httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
    }

    @RequestMapping(value = "/companies/{compId}/survey/{survId}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE,method = RequestMethod.GET)
    public SurveyDtoRest getSurvey(@PathVariable(name="compId")Integer compId, @PathVariable("survId")Integer survId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        Survey survey=companySurveyService.getSurveyByIdWithQuestion(survId);
        if(survey.getCompany().getId()!=compId){
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return null;
        }
        VoucherCodeDate voucherCodeDate;
        HttpSession session=httpServletRequest.getSession(true);
        try{
            if(session.getAttribute("vCode")!=null){
                Integer id=(Integer) session.getAttribute("vCode");
                companySurveyService.unBlockVoucherCode(id);
            }
            voucherCodeDate=companySurveyService.blockVoucherCodeForSurvey(survId);
            session.setAttribute("vCode", voucherCodeDate.getId());
        }catch(NoAvaibleVouchersException e){
            httpServletResponse.setStatus(HttpServletResponse.SC_GONE);
            return null;
        }
        return new SurveyDtoRest(survey);
    }

    @RequestMapping(value = "voucher/unblock",produces = MediaType.APPLICATION_JSON_UTF8_VALUE,method = RequestMethod.POST)
    public String unBlockVoucherCode(HttpServletRequest httpServletRequest){
        HttpSession httpSession=httpServletRequest.getSession(false);
        Integer vCode=(Integer) httpSession.getAttribute("vCode");
        if(vCode!=null){
            companySurveyService.unBlockVoucherCode(vCode);
            httpSession.setAttribute("vCode",null);
            return "OK";
        }else {
            return "NOT FOUND";
        }
    }
}
