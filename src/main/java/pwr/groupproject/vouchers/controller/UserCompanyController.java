package pwr.groupproject.vouchers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pwr.groupproject.vouchers.bean.dto.SurveyDto;
import pwr.groupproject.vouchers.bean.dto.SurveyStatisticsDto;
import pwr.groupproject.vouchers.bean.exceptions.WrongSurveyIdException;
import pwr.groupproject.vouchers.bean.form.*;
import pwr.groupproject.vouchers.bean.model.*;
import pwr.groupproject.vouchers.bean.model.enums.DiscountType;
import pwr.groupproject.vouchers.bean.model.security.UserCompany;
import pwr.groupproject.vouchers.services.CompanySurveyService;
import pwr.groupproject.vouchers.services.StatisticsService;
import pwr.groupproject.vouchers.services.UserCompanyService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(UserCompanyController.ROOT_MAPPING)
@PreAuthorize("hasRole('COMPANY')")
public class UserCompanyController {
    static final String ROOT_MAPPING = "/my_account";
    private final CompanySurveyService companySurveyService;
    private final UserCompanyService userCompanyService;
    private final ShaPasswordEncoder passwordEncoder;
    private final MessageSource messageSource;
    private final StatisticsService statisticsService;

    @Autowired
    public UserCompanyController(CompanySurveyService companySurveyService, UserCompanyService userCompanyService, ShaPasswordEncoder passwordEncoder, MessageSource messageSource, StatisticsService statisticsService) {
        this.companySurveyService = companySurveyService;
        this.userCompanyService = userCompanyService;
        this.passwordEncoder = passwordEncoder;
        this.messageSource = messageSource;
        this.statisticsService = statisticsService;
    }

    //region Home Management
    @RequestMapping("/home")
    public String homePage(Model model) {
        return "my_account/home_page.html";
    }
    //endregion

    //region Account Management
    @RequestMapping(value = "/account_panel",method = RequestMethod.GET)
    public String accountPanel(Model model, @AuthenticationPrincipal UserCompany userCompany,@RequestParam(required = false,name = "dAcc")Integer triedToDeleteAcc) {
        if(triedToDeleteAcc!=null && triedToDeleteAcc==1){
            model.addAttribute("error",messageSource.getMessage("account.delete", null, LocaleContextHolder.getLocale()));
        }
        Company company= companySurveyService.getUsersCompany(userCompany);
        prepareAccountPanelModel(model,company);
        return "my_account/account_panel.html";
    }

    @RequestMapping(value = "/account_panel/companyDetails", method = RequestMethod.POST)
    public String changeCompanyName(Model model, @AuthenticationPrincipal UserCompany userCompany, @Validated @ModelAttribute(name="companyForm") CompanyDetailsForm companyDetailsForm, BindingResult bindingResult, @ModelAttribute(name="addressForm") AddressForm addressForm, @ModelAttribute(name="passwordForm") PasswordForm passwordForm) {
        Company company = companySurveyService.getUsersCompany(userCompany);

        if(bindingResult.hasErrors()){
            Address currentAddress=company.getCompanyAddress();
            addressForm.setAddressDetails(currentAddress.getAddressDetails());
            addressForm.setCity(currentAddress.getCity());
            addressForm.setPostalCode(currentAddress.getPostalCode());

            model.addAttribute("addressForm",addressForm);
            model.addAttribute("passwordForm",passwordForm);
            return "my_account/account_panel.html";
        }

        company.setCompanyName(companyDetailsForm.getCompanyName());
        companySurveyService.updateCompany(company);

        prepareAccountPanelModel(model,company);
        return "my_account/account_panel.html";
    }


    @RequestMapping(value = "/account_panel/companyAddress", method = RequestMethod.POST)
    public String changeCompanyAddress(Model model, @AuthenticationPrincipal UserCompany userCompany, @ModelAttribute(name="companyForm") CompanyDetailsForm companyDetailsForm, @Validated @ModelAttribute(name="addressForm") AddressForm addressForm,BindingResult bindingResult, @ModelAttribute(name="passwordForm") PasswordForm passwordForm) {
        Company company = companySurveyService.getUsersCompany(userCompany);

        if(bindingResult.hasErrors()){
            companyDetailsForm.setCompanyName(company.getCompanyName());

            model.addAttribute("passwordForm",passwordForm);
            model.addAttribute("companyForm",companyDetailsForm);
            return "my_account/account_panel.html";
        }

        Address currentAddress=company.getCompanyAddress();
        currentAddress.setPostalCode(addressForm.getPostalCode());
        currentAddress.setCity(addressForm.getCity());
        currentAddress.setAddressDetails(addressForm.getAddressDetails());
        companySurveyService.updateCompany(company);

        prepareAccountPanelModel(model,company);
        return "my_account/account_panel.html";
    }

    @RequestMapping(value = "/account_panel/password", method = RequestMethod.POST)
    public String changePassword(Model model, @AuthenticationPrincipal UserCompany userCompany, @ModelAttribute(name="companyForm") CompanyDetailsForm companyDetailsForm, @ModelAttribute(name="addressForm") AddressForm addressForm, @Validated @ModelAttribute(name="passwordForm") PasswordForm passwordForm,BindingResult bindingResult) {
        Company company = companySurveyService.getUsersCompany(userCompany);

        String hashedOldPassword=passwordEncoder.encodePassword(passwordForm.getOldPassword(),null);
        if(!bindingResult.hasErrors() && !hashedOldPassword.equals(userCompany.getPassword())){
            bindingResult.rejectValue("oldPassword", "wrong.old.password", messageSource.getMessage("message.wrong.old.password", null, LocaleContextHolder.getLocale()));
        }
        if(bindingResult.hasErrors()){
            Address currentAddress=company.getCompanyAddress();
            addressForm.setAddressDetails(currentAddress.getAddressDetails());
            addressForm.setCity(currentAddress.getCity());
            addressForm.setPostalCode(currentAddress.getPostalCode());

            companyDetailsForm.setCompanyName(company.getCompanyName());

            model.addAttribute("addressForm",addressForm);
            model.addAttribute("companyForm",companyDetailsForm);
            return "my_account/account_panel.html";
        }

        String hashedNewPassword=passwordEncoder.encodePassword(passwordForm.getPassword(),null);
        userCompanyService.changePassword(userCompany.getUsername(),hashedNewPassword);

        prepareAccountPanelModel(model,company);
        return "my_account/account_panel.html";
    }

    @RequestMapping(value = "/account_panel/delete", method = RequestMethod.POST)
    public String deleteAccount(@AuthenticationPrincipal UserCompany userCompany, Model model, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
        if(companySurveyService.getCompanyWithSurveys(userCompany).getCompanysSurveys().size()!=0){
            redirectAttributes.addAttribute("dAcc","1");
            return "redirect:/"+"my_account/account_panel";
        }else{
            HttpSession session=httpServletRequest.getSession(false);
            if(session!=null)
                session.invalidate();
        }
        userCompanyService.deleteUserCompany(userCompany.getId());
        return "redirect:/";

    }

    private void prepareAccountPanelModel(Model model,Company company){
        Address currentAddress=company.getCompanyAddress();
        AddressForm addressForm=new AddressForm();
        addressForm.setAddressDetails(currentAddress.getAddressDetails());
        addressForm.setCity(currentAddress.getCity());
        addressForm.setPostalCode(currentAddress.getPostalCode());

        CompanyDetailsForm companyDetailsForm=new CompanyDetailsForm();
        companyDetailsForm.setCompanyName(company.getCompanyName());

        PasswordForm passwordForm=new PasswordForm();

        model.addAttribute("addressForm",addressForm);
        model.addAttribute("companyForm",companyDetailsForm);
        model.addAttribute("passwordForm",passwordForm);
    }
    //endregion

    //region Survey Management
    //region Viewing Surveys
    @RequestMapping(value = "/surveys", method = RequestMethod.GET)
    public String surveys(Model model, @AuthenticationPrincipal UserCompany userCompany) {
        Company companyWithSurveys = companySurveyService.getCompanyWithSurveys(userCompany);
        model.addAttribute("surveyList", companyWithSurveys.getCompanysSurveys());
        return "my_account/surveys/surveys.html";
    }

    @RequestMapping(value = "/surveys/{id}", method = RequestMethod.GET)
    public String surveyDetails(@PathVariable("id") int surveyId, Model model, @AuthenticationPrincipal UserCompany userCompany) {
        try {
            Survey survey = companySurveyService.checkIfSurveyExists(surveyId, userCompany);
            model.addAttribute("survey", survey);
            return "my_account/surveys/survey_details.html";
        } catch (WrongSurveyIdException ex) {
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

    @RequestMapping(value = "/surveys/add", method = RequestMethod.GET)
    public String newSurvey(Model model) {
        return "my_account/surveys/create_survey.html";
    }
    //endregion
    //endregion

    //region Voucher Management
    //region Concrete Voucher Management
    @RequestMapping(value = "/surveys/{id}/voucher", method = RequestMethod.GET)
    public String manageSurveysVoucher(@PathVariable("id") int surveyId, Model model, @AuthenticationPrincipal UserCompany userCompany) {
        if(!checkForSurveyExistence(surveyId,userCompany)){
            return "error.html";
        }
        prepareVoucherPanelModel(model, companySurveyService.getSurveyById(surveyId),true,true);
        return "/my_account/vouchers/manage_voucher.html";
    }

    @RequestMapping(value = "/surveys/{id}/voucher/editVoucher", method = RequestMethod.POST)
    public String changeVoucherDetails(Model model,@AuthenticationPrincipal UserCompany userCompany,@PathVariable("id") int surveyId, @Validated @ModelAttribute(name="voucherForm") VoucherForm voucherForm,BindingResult bindingResult) {
        if(!checkForSurveyExistence(surveyId,userCompany)){
            return "error.html";
        }
        if(voucherForm.getStartDate()!=null && voucherForm.getEndDate()!=null && voucherForm.getStartDate().after(voucherForm.getEndDate())){
            bindingResult.reject("startDate");
        }if(bindingResult.hasErrors()){
            prepareVoucherPanelModel(model,companySurveyService.getSurveyById(surveyId),true,false);
            return "/my_account/vouchers/manage_voucher.html";
        }
        Voucher voucher = companySurveyService.getSurveyById(surveyId).getVoucher();
        companySurveyService.updateVoucher(voucher,voucherForm);

        Survey survey = companySurveyService.getSurveyById(surveyId);
        prepareVoucherPanelModel(model, survey,true,true);
        return "/my_account/vouchers/manage_voucher.html";
    }

    private void prepareVoucherPanelModel(Model model, Survey survey,boolean addVoucherCodeForm, boolean addVoucherForm) {
        model.addAttribute("surveyName", survey.getSurveyName());
        model.addAttribute("voucher", survey.getVoucher());
        model.addAttribute("surveyId", survey.getId());
        model.addAttribute("discountType", DiscountType.values());
        if(addVoucherCodeForm) {
            model.addAttribute("voucherCodeForm", new VoucherCodeForm());
        }
        if(addVoucherForm) {
            Voucher voucher = survey.getVoucher();
            VoucherForm voucherForm = new VoucherForm();
            voucherForm.setDiscountAmount(voucher.getDiscountAmount());
            voucherForm.setDiscountType(voucher.getDiscountType());
            voucherForm.setEndDate(voucher.getEndDate());
            voucherForm.setStartDate(voucher.getStartDate());
            voucherForm.setVoucherDescription(voucher.getVoucherDescription());
            model.addAttribute("voucherForm", voucherForm);
        }
    }
    private boolean checkForSurveyExistence(int surveyId, UserCompany userCompany){
        try {
            if (companySurveyService.checkIfSurveyExists(surveyId, userCompany) == null) {
                return false;
            }
        }
        catch (WrongSurveyIdException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }
    //endregion

    //region Codes Management
    @RequestMapping(value = "/surveys/{id}/voucher/changeCode", method = RequestMethod.POST)
    public String changeVoucherCode(Model model, @PathVariable("id") int surveyId, @AuthenticationPrincipal UserCompany userCompany, @RequestParam(name = "codeId") Integer codeId, @RequestParam(name = "numberOfCodes") Integer numberOfCodes) {
        if(!checkForSurveyExistence(surveyId,userCompany)){
            return "error.html";
        }
        VoucherCode voucherCode=companySurveyService.getVoucherCodeById(codeId);
        if(numberOfCodes>=0 && voucherCode.getVoucher().getSurvey().getId()==surveyId){
            voucherCode.setAmmountOfUses(numberOfCodes);
            companySurveyService.updateVoucherCode(voucherCode);
        }
        Survey survey = companySurveyService.getSurveyById(surveyId);

        prepareVoucherPanelModel(model,survey,true,true);
        return "/my_account/vouchers/manage_voucher.html";
    }

    @RequestMapping(value = "/surveys/{id}/voucher/addCode", method = RequestMethod.POST)
    public String addVoucherCode(Model model,@AuthenticationPrincipal UserCompany userCompany, @PathVariable("id") int surveyId, @Validated @ModelAttribute(name="voucherCodeForm") VoucherCodeForm voucherCodeForm, BindingResult bindingResult) {
        if(!checkForSurveyExistence(surveyId,userCompany)){
            return "error.html";
        }
        Survey survey = companySurveyService.getSurveyById(surveyId);
        if(bindingResult.hasErrors()){
            prepareVoucherPanelModel(model,survey,false,true);
            return "/my_account/vouchers/manage_voucher.html";
        }
        else{
            prepareVoucherPanelModel(model,survey,true,true);
        }
        VoucherCode voucherCode = new VoucherCode();
        voucherCode.setVoucherCode(voucherCodeForm.getVoucherCode());
        voucherCode.setAmmountOfUses(voucherCodeForm.getAmountOfUses());
        companySurveyService.addVoucherCode(voucherCode, companySurveyService.getSurveyById(surveyId).getVoucher().getId());
        return "/my_account/vouchers/manage_voucher.html";
    }

    @RequestMapping(value = "/surveys/{id}/voucher/deleteCode", method = RequestMethod.POST)
    public String deleteVoucherCode(Model model,@AuthenticationPrincipal UserCompany userCompany, @PathVariable("id") int surveyId, @RequestParam(name = "codeId") Integer codeId) {
        if(!checkForSurveyExistence(surveyId,userCompany)){
            return "error.html";
        }
        VoucherCode voucherCode=companySurveyService.getVoucherCodeById(codeId);
        if(voucherCode.getVoucher().getSurvey().getId()==surveyId)
            companySurveyService.deleteVoucherCode(codeId);
        Survey survey = companySurveyService.getSurveyById(surveyId);
        prepareVoucherPanelModel(model, survey, true,true);
        return "/my_account/vouchers/manage_voucher.html";
    }
    //endregion

    //region Adding Voucher
    @RequestMapping(value = "/surveys/{id}/addVoucher", method = RequestMethod.GET)
    public String voucher(@AuthenticationPrincipal UserCompany userCompany, @PathVariable("id") int surveyId, Model model) {
        if(!checkForSurveyExistence(surveyId,userCompany)){
            return "error.html";
        }
        model.addAttribute("surveyId", surveyId);
        model.addAttribute("discountType", DiscountType.values());
        model.addAttribute("voucherForm",new VoucherForm());

        return "my_account/vouchers/add_voucher";
    }

    @RequestMapping(value = "/surveys/{id}/addVoucher", method = RequestMethod.POST)
    public String addVouchersForm(Model model,@AuthenticationPrincipal UserCompany userCompany,@PathVariable("id") int surveyId, @Validated @ModelAttribute(name="voucherForm") VoucherForm voucherForm,BindingResult bindingResult){

        if(!checkForSurveyExistence(surveyId,userCompany)){
            return "error.html";
        }
        if(voucherForm.getStartDate()!=null && voucherForm.getEndDate()!=null && voucherForm.getStartDate().after(voucherForm.getEndDate())){
            bindingResult.reject("startDate");
        }if(bindingResult.hasErrors()){
            model.addAttribute("surveyId", surveyId);
            model.addAttribute("discountType", DiscountType.values());
            return "my_account/vouchers/add_voucher";
        }
        Voucher voucher = new Voucher();
        voucher.setDiscountType(voucherForm.getDiscountType());
        voucher.setDiscountAmount(voucherForm.getDiscountAmount());
        voucher.setVoucherDescription(voucherForm.getVoucherDescription());
        voucher.setStartDate(voucherForm.getStartDate());
        voucher.setEndDate(voucherForm.getEndDate());
        companySurveyService.addVoucher(voucher, surveyId);
        return "redirect:/my_account/surveys";
    }
    //endregion
    //endregion

    //region Statistics Managment
    @RequestMapping("/statistics/{id}")
    public String statistics(@PathVariable("id") int surveyId, Model model, @AuthenticationPrincipal UserCompany userCompany) {
        if(!checkForSurveyExistence(surveyId,userCompany)){
            return "error.html";
        }
        /*Survey survey;
        try {
            survey = companySurveyService.checkIfSurveyExists(surveyId, userCompany);
        } catch (WrongSurveyIdException ex) {
            ex.printStackTrace();
            return "/error.html"; //I have no idea how to tell spring that error occured properly :( . FIX IT PLZ!
        }*/
        SurveyStatisticsDto stats = statisticsService.getSurveysStatistics(surveyId);
        //model.addAttribute("survey", survey);
        model.addAttribute("stats", stats);
        return "my_account/stats/survey_stat.html";
    }
    //endregion
}


