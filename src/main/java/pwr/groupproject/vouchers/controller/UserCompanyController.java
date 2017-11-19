package pwr.groupproject.vouchers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pwr.groupproject.vouchers.bean.dto.SurveyDto;
import pwr.groupproject.vouchers.bean.exceptions.WrongSurveyIdException;
import pwr.groupproject.vouchers.bean.model.*;
import pwr.groupproject.vouchers.bean.model.enums.DiscountType;
import pwr.groupproject.vouchers.bean.model.security.UserCompany;
import pwr.groupproject.vouchers.services.CompanySurveyService;
import pwr.groupproject.vouchers.services.UserCompanyService;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

@Controller
@RequestMapping(UserCompanyController.ROOT_MAPPING)
@PreAuthorize("hasRole('COMPANY')")
public class UserCompanyController {
    public static final String ROOT_MAPPING = "/my_account";
    @Autowired
    private CompanySurveyService companySurveyService;
    @Autowired
    private UserCompanyService userCompanyService;

    //region Home Management
    @RequestMapping("/home")
    public String homePage(Model model) {
        return "my_account/home_page.html";
    }
    //endregion

    //region Account Management
    @RequestMapping("/account_panel")
    public String accountPanel(Model model, @AuthenticationPrincipal UserCompany userCompany) {
        model.addAttribute("company", userCompany.getCompany());
        return "my_account/account_panel.html";
    }

    @RequestMapping(value = "/account_panel/companyName" ,method = RequestMethod.POST)
    public String changeCompanyName(Model model, @AuthenticationPrincipal UserCompany userCompany, @RequestParam(name="companyName", required = true) String companyName) {
        Company company = userCompany.getCompany();
        company.setCompanyName(companyName);
        System.out.println(company.getCompanyName());
        companySurveyService.updateCompany(company);
        return "redirect:/my_account/account_panel";
    }

    @RequestMapping(value= "/account_panel/companyAddress",method = RequestMethod.POST)
    public String changeCompanyAddress(Model model, @AuthenticationPrincipal UserCompany userCompany, @RequestParam(name="addressDetails", required = true) String addressDetails, @RequestParam(name="city", required = true) String city, @RequestParam(name="postalCode", required = true) String postalCode) {
        Address address = new Address();
        address.setPostalCode(postalCode);
        address.setCity(city);
        address.setAddressDetails(addressDetails);
        Company company = userCompany.getCompany();
        company.setCompanyAddress(address);
        companySurveyService.updateCompany(company);
        return "redirect:/my_account/account_panel";
    }

    @RequestMapping(value = "/account_panel/delete",method = RequestMethod.POST)
    public String deleteAccount(@AuthenticationPrincipal UserCompany userCompany, Model model) {
        companySurveyService.deleteCompany(userCompany.getCompany().getId());
        return "redirect:/";
    }
    //endregion

    //region Survey Management
    //region Viewing Surveys
    @RequestMapping(value = "/surveys",method = RequestMethod.GET)
    public String surveys(Model model, @AuthenticationPrincipal UserCompany userCompany) {
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
    //endregion

    //region Adding Survey
    @RequestMapping(value = "/surveys/add", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public String newSurvey(@RequestBody SurveyDto surveyDto, @AuthenticationPrincipal UserCompany userCompany, HttpServletResponse httpServletResponse) {
        try {
            companySurveyService.addSurvey(surveyDto, userCompany);
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
    //endregion
    //endregion

    //region Voucher Management
    //region Concrete Voucher Management
    @RequestMapping(value="/surveys/{id}/voucher",method = RequestMethod.GET)
    public String manageSurveysVoucher(@PathVariable("id") int surveyId,Model model,@AuthenticationPrincipal UserCompany userCompany){
        try {
            Survey survey = companySurveyService.checkIfSurveyExists(surveyId, userCompany);
            model.addAttribute("surveyName",survey.getSurveyName());
            model.addAttribute("voucher",survey.getVoucher());
            model.addAttribute("surveyId",surveyId);
            model.addAttribute("discountType", DiscountType.values());
            return "/my_account/vouchers/manage_voucher.html";
        }catch(WrongSurveyIdException ex){
            ex.printStackTrace();
            return "/error.html";
        }
    }

    @RequestMapping(value="/surveys/{id}/voucher/details",method = RequestMethod.POST)
    public String changeVoucherDetails(@PathVariable("id") int surveyId, @RequestParam(name="details", required = true) String details){
        Voucher voucher = companySurveyService.getSurveyById(surveyId).getVoucher();
        voucher.setVoucherDescription(details);
        companySurveyService.updateVoucher(voucher);
        return "redirect:/my_account/surveys";
    }

    @RequestMapping(value="/surveys/{id}/voucher/discountType",method = RequestMethod.POST)
    public String changeVoucherDiscountType(@PathVariable("id") int surveyId, @RequestParam(name="discountType", required = true) DiscountType discountType){
        Voucher voucher = companySurveyService.getSurveyById(surveyId).getVoucher();
        voucher.setDiscountType(discountType);
        companySurveyService.updateVoucher(voucher);
        return "redirect:/my_account/surveys";
    }
    @RequestMapping(value="/surveys/{id}/voucher/discountAmount",method = RequestMethod.POST)
    public String changeVoucherDiscountAmount(@PathVariable("id") int surveyId, @RequestParam(name="discountAmount", required = true) Integer discountAmount){
        Voucher voucher = companySurveyService.getSurveyById(surveyId).getVoucher();
        voucher.setDiscountAmount(discountAmount);
        companySurveyService.updateVoucher(voucher);
        return "redirect:/my_account/surveys";
    }
    @RequestMapping(value="/surveys/{id}/voucher/startDate",method = RequestMethod.POST)
    public String changeVoucherStartDate(@PathVariable("id") int surveyId, @RequestParam(name="startDate", required = true) String startDate){
        Voucher voucher = companySurveyService.getSurveyById(surveyId).getVoucher();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            voucher.setStartDate(formatter.parse(startDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        companySurveyService.updateVoucher(voucher);
        return "redirect:/my_account/surveys";
    }

    @RequestMapping(value="/surveys/{id}/voucher/endDate",method = RequestMethod.POST)
    public String changeVoucherEndDate(@PathVariable("id") int surveyId, @RequestParam(name="endDate", required = true) String endDate){
        Voucher voucher = companySurveyService.getSurveyById(surveyId).getVoucher();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            voucher.setEndDate(formatter.parse(endDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        companySurveyService.updateVoucher(voucher);
        return "redirect:/my_account/surveys";
    }

    //endregion

    //region Codes Management
    @RequestMapping(value="/surveys/{id}/voucher/changeCode",method = RequestMethod.POST)
    public String changeVoucherCode(@PathVariable("id") int surveyId, @RequestParam(name="numberOfCodes", required = true) Integer numberOfCodes, @RequestParam(name="codeId", required = true) Integer codeId){
        Survey survey = companySurveyService.getSurveyById(surveyId);
        for (VoucherCode code:survey.getVoucher().getCodes()) {
            if(code.getId()==codeId){
                code.setAmmountOfUses(numberOfCodes);
                companySurveyService.updateVoucherCode(code);
                break;
            }
        }
        return "redirect:/my_account/surveys";
    }

    //orphanRemoval = true by cascade = CascadeType.ALL in Voucher for it to work
    @RequestMapping(value="/surveys/{id}/voucher/addCode",method = RequestMethod.POST)
    public String addVoucherCode(@PathVariable("id") int surveyId, @RequestParam(name="numberOfCodes", required = true) Integer numberOfCodes, @RequestParam(name="code", required = true) String code){
        VoucherCode voucherCode = new VoucherCode();
        voucherCode.setVoucherCode(code);
        voucherCode.setAmmountOfUses(numberOfCodes);
        companySurveyService.addVoucherCode(voucherCode, companySurveyService.getSurveyById(surveyId).getVoucher().getId());
        return "redirect:/my_account/surveys";
    }

    //cascade = CascadeType.ALL by orphanRemoval = true in Voucher for it to work
    @RequestMapping(value="/surveys/{id}/voucher/deleteCode",method = RequestMethod.POST)
    public String deleteVoucherCode(@PathVariable("id") int surveyId, @RequestParam(name="codeId", required = true) Integer codeId){
        for (VoucherCode code:companySurveyService.getSurveyById(surveyId).getVoucher().getCodes()) {
            if(code.getId()==codeId){
                companySurveyService.deleteVoucherCode(codeId);
                break;
            }
        }
        return "redirect:/my_account/surveys";
    }
    //endregion

    //region Adding Voucher
    @RequestMapping(value="/surveys/{id}/addVoucher",method = RequestMethod.GET)
    public String voucher(@PathVariable("id") int surveyId, Model model){
        model.addAttribute("surveyId", surveyId);
        model.addAttribute("discountType", DiscountType.values());
        return "my_account/vouchers/add_voucher";
    }

    @RequestMapping(value="/surveys/{id}/addVoucher",method = RequestMethod.POST)
    public String addVoucher(@PathVariable("id") int surveyId, @RequestParam(name="discountType", required = true) DiscountType discountType, @RequestParam(name="discountAmount", required = true) Integer discountAmount, @RequestParam(name="description", required = true) String description, @RequestParam(name="startDate", required = true) String startDate, @RequestParam(name="endDate", required = true) String endDate  ){
        Voucher voucher = new Voucher();
        voucher.setDiscountType(discountType);
        voucher.setDiscountAmount(discountAmount);
        voucher.setVoucherDescription(description);
        System.out.println(startDate+ "   "+endDate);
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            voucher.setStartDate(formatter.parse(startDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            voucher.setEndDate(formatter.parse(endDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        companySurveyService.addVoucher(voucher, surveyId);
        return "redirect:/my_account/surveys";
    }
    //endregion
    //endregion

    //region Statistics Managment
    @RequestMapping("/statistics")
    public String statistics(Model model) {
        return "my_account/stats/stats_homePage";
    }
    //endregion
}


