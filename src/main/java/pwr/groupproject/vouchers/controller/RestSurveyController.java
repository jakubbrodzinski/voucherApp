package pwr.groupproject.vouchers.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pwr.groupproject.vouchers.bean.dto.AnswerDto;
import pwr.groupproject.vouchers.bean.dto.rest.*;
import pwr.groupproject.vouchers.bean.exceptions.NoAvaibleVouchersException;
import pwr.groupproject.vouchers.bean.exceptions.WrongCompanyIdException;
import pwr.groupproject.vouchers.bean.model.Survey;
import pwr.groupproject.vouchers.bean.model.Voucher;
import pwr.groupproject.vouchers.bean.model.VoucherCode;
import pwr.groupproject.vouchers.bean.model.VoucherCodeDate;
import pwr.groupproject.vouchers.services.CompanySurveyService;
import pwr.groupproject.vouchers.services.MailService;
import pwr.groupproject.vouchers.services.RestService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.TreeMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping(RestSurveyController.ROOT_MAPPING)
public class RestSurveyController {
    static final String ROOT_MAPPING = "/api/rest";
    private final CompanySurveyService companySurveyService;
    private final RestService restService;
    private final MailService mailService;

    @Autowired
    public RestSurveyController(RestService restService,CompanySurveyService companySurveyService,MailService mailService) {
        this.restService = restService;
        this.companySurveyService=companySurveyService;
        this.mailService=mailService;
    }

    @RequestMapping(value = "/companies",produces = MediaType.APPLICATION_JSON_UTF8_VALUE,method = RequestMethod.GET)
    public Collection<CompanyDtoRest> getAllActiveCompanies(){
        return companySurveyService.getAllActiveCompanies().stream().map(CompanyDtoRest::new).collect(Collectors.toList());
    }

    @RequestMapping(value = "/companies/{compId}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE,method = RequestMethod.GET)
    public Collection<SurveyVoucherDtoRest> getCompanysActiveSurveys(@PathVariable(name = "compId")Integer compId, HttpServletResponse httpServletResponse){
        try{
            Collection<Survey> activeSurveys= companySurveyService.getAllActiveSurveys(compId);
            return activeSurveys.stream().map(SurveyVoucherDtoRest::new).collect(Collectors.toList());
        }catch(WrongCompanyIdException e){
            httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
    }

    @RequestMapping(value = "/companies/{compId}/survey/{survId}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE,method = RequestMethod.GET)
    public SurveyDtoRest getSurvey(@PathVariable(name="compId")Integer compId, @PathVariable("survId")Integer survId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        Survey survey= companySurveyService.getSurveyByIdWithQuestion(survId);
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
            voucherCodeDate= companySurveyService.blockVoucherCodeForSurvey(survId);
            session.setAttribute("vCode", voucherCodeDate.getId());
        }catch(NoAvaibleVouchersException e){
            httpServletResponse.setStatus(HttpServletResponse.SC_GONE);
            return null;
        }
        return new SurveyDtoRest(survey);
    }

    @RequestMapping(value = "/companies/{compId}/survey/{survId}",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,method = RequestMethod.POST)
    public AnsweredSurveyReplyDtoRest insertFilledSurvey(@PathVariable(name="compId")Integer compId, @PathVariable("survId")Integer survId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,@RequestBody AnsweredSurveyDtoRest answeredSurveyDtoRest){
        Survey survey= companySurveyService.getSurveyByIdWithQuestion(survId);
        if(survey.getCompany().getId()!=compId){
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return null;
        }
        HttpSession session=httpServletRequest.getSession(true);
        //if there is no session
        Integer vCodeId=(Integer) session.getAttribute("vCode");
        if(vCodeId==null){
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return null;
        }
        //validating answers
        AnsweredSurveyReplyDtoRest answeredSurveyReplyDtoRest=new AnsweredSurveyReplyDtoRest();
        TreeMap<Integer, String> errMap=restService.validateAnswerAndDeployVoucher(answeredSurveyDtoRest,survId);
        if (errMap.size()==0){
            session.setAttribute("vCode", null);
            VoucherCode voucherCode=companySurveyService.deployVoucherCode(vCodeId);
            Voucher voucher= voucherCode.getVoucher();
            answeredSurveyReplyDtoRest.setErrors(null);
            answeredSurveyReplyDtoRest.setDiscountAmount(voucher.getDiscountAmount());
            answeredSurveyReplyDtoRest.setDiscountType(voucher.getDiscountType());
            answeredSurveyReplyDtoRest.setEndDate(voucher.getEndDate());
            answeredSurveyReplyDtoRest.setStartDate(voucher.getStartDate());
            answeredSurveyReplyDtoRest.setVoucherDescription(voucher.getVoucherDescription());
            answeredSurveyReplyDtoRest.setVoucherCode(voucherCode.getVoucherCode());
            restService.addAnsweredSurvey(answeredSurveyDtoRest,survId);
            mailService.sendVoucherCodeEmail(voucherCode, answeredSurveyDtoRest.getEmail());
        }else{
            answeredSurveyReplyDtoRest.setErrors(errMap);
        }
        return answeredSurveyReplyDtoRest;
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
            return "NOT_FOUND";
        }
    }
//Pokazuje strukture AnsweredSurveyDtoRest
    @RequestMapping(value = "test1",produces = MediaType.APPLICATION_JSON_UTF8_VALUE,method = RequestMethod.GET)
    public AnsweredSurveyDtoRest test(){
        AnsweredSurveyDtoRest answeredSurveyDtoRest=new AnsweredSurveyDtoRest();
        answeredSurveyDtoRest.setAge(13);
        answeredSurveyDtoRest.setCountry("PL");
        answeredSurveyDtoRest.setEmail(null);
        AnswerDto answerDto=new AnswerDto();
        answerDto.setAnswerBody("body1");
        AnswerDto answerDto2=new AnswerDto();
        answerDto2.setAnswerBody("body2");
        AnswerDto[] array=new AnswerDto[2];
        answeredSurveyDtoRest.getAnswersMap().put(0,"body1");
        answeredSurveyDtoRest.getAnswersMap().put(1,"body2");
        return answeredSurveyDtoRest;
    }
}
