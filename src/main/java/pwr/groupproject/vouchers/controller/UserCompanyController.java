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
import pwr.groupproject.vouchers.bean.dto.SurveyDto;
import pwr.groupproject.vouchers.bean.exceptions.WrongSurveyIdException;
import pwr.groupproject.vouchers.bean.form.AddressForm;
import pwr.groupproject.vouchers.bean.form.CompanyDetailsForm;
import pwr.groupproject.vouchers.bean.form.PasswordForm;
import pwr.groupproject.vouchers.bean.form.VoucherForm;
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
    @Autowired
    private ShaPasswordEncoder passwordEncoder;
    @Autowired
    private MessageSource messageSource;

    //region Home Management
    @RequestMapping("/home")
    public String homePage(Model model) {
        return "my_account/home_page.html";
    }
    //endregion

    //region Account Management
    @RequestMapping(value = "/account_panel",method = RequestMethod.GET)
    public String accountPanel(Model model, @AuthenticationPrincipal UserCompany userCompany) {
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
    public String deleteAccount(@AuthenticationPrincipal UserCompany userCompany, Model model) {
        companySurveyService.deleteCompany(userCompany.getCompany().getId());
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
        try {
            Survey survey = companySurveyService.checkIfSurveyExists(surveyId, userCompany);
            model.addAttribute("surveyName", survey.getSurveyName());
            model.addAttribute("voucher", survey.getVoucher());
            model.addAttribute("surveyId", surveyId);
            model.addAttribute("discountType", DiscountType.values());
            return "/my_account/vouchers/manage_voucher.html";
        } catch (WrongSurveyIdException ex) {
            ex.printStackTrace();
            return "/error.html";
        }
    }

    @RequestMapping(value = "/surveys/{id}/voucher/details", method = RequestMethod.POST)
    public String changeVoucherDetails(@PathVariable("id") int surveyId, @RequestParam(name = "details", required = true) String details) {
        Voucher voucher = companySurveyService.getSurveyById(surveyId).getVoucher();
        voucher.setVoucherDescription(details);
        companySurveyService.updateVoucher(voucher);
        return "redirect:/my_account/surveys";
    }

    @RequestMapping(value = "/surveys/{id}/voucher/discountType", method = RequestMethod.POST)
    public String changeVoucherDiscountType(@PathVariable("id") int surveyId, @RequestParam(name = "discountType", required = true) DiscountType discountType) {
        Voucher voucher = companySurveyService.getSurveyById(surveyId).getVoucher();
        voucher.setDiscountType(discountType);
        companySurveyService.updateVoucher(voucher);
        return "redirect:/my_account/surveys";
    }

    @RequestMapping(value = "/surveys/{id}/voucher/discountAmount", method = RequestMethod.POST)
    public String changeVoucherDiscountAmount(@PathVariable("id") int surveyId, @RequestParam(name = "discountAmount", required = true) Integer discountAmount) {
        Voucher voucher = companySurveyService.getSurveyById(surveyId).getVoucher();
        voucher.setDiscountAmount(discountAmount);
        companySurveyService.updateVoucher(voucher);
        return "redirect:/my_account/surveys";
    }

    @RequestMapping(value = "/surveys/{id}/voucher/startDate", method = RequestMethod.POST)
    public String changeVoucherStartDate(@PathVariable("id") int surveyId, @RequestParam(name = "startDate", required = true) String startDate) {
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

    @RequestMapping(value = "/surveys/{id}/voucher/endDate", method = RequestMethod.POST)
    public String changeVoucherEndDate(@PathVariable("id") int surveyId, @RequestParam(name = "endDate", required = true) String endDate) {
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
    @RequestMapping(value = "/surveys/{id}/voucher/changeCode", method = RequestMethod.POST)
    public String changeVoucherCode(@PathVariable("id") int surveyId, @RequestParam(name = "numberOfCodes", required = true) Integer numberOfCodes, @RequestParam(name = "codeId", required = true) Integer codeId) {
        Survey survey = companySurveyService.getSurveyById(surveyId);
        for (VoucherCode code : survey.getVoucher().getCodes()) {
            if (code.getId() == codeId) {
                code.setAmmountOfUses(numberOfCodes);
                companySurveyService.updateVoucherCode(code);
                break;
            }
        }
        return "redirect:/my_account/surveys";
    }

    //orphanRemoval = true by cascade = CascadeType.ALL in Voucher for it to work
    @RequestMapping(value = "/surveys/{id}/voucher/addCode", method = RequestMethod.POST)
    public String addVoucherCode(@PathVariable("id") int surveyId, @RequestParam(name = "numberOfCodes", required = true) Integer numberOfCodes, @RequestParam(name = "code", required = true) String code) {
        VoucherCode voucherCode = new VoucherCode();
        voucherCode.setVoucherCode(code);
        voucherCode.setAmmountOfUses(numberOfCodes);
        companySurveyService.addVoucherCode(voucherCode, companySurveyService.getSurveyById(surveyId).getVoucher().getId());
        return "redirect:/my_account/surveys";
    }

    //cascade = CascadeType.ALL by orphanRemoval = true in Voucher for it to work
    @RequestMapping(value = "/surveys/{id}/voucher/deleteCode", method = RequestMethod.POST)
    public String deleteVoucherCode(@PathVariable("id") int surveyId, @RequestParam(name = "codeId", required = true) Integer codeId) {
        for (VoucherCode code : companySurveyService.getSurveyById(surveyId).getVoucher().getCodes()) {
            if (code.getId() == codeId) {
                companySurveyService.deleteVoucherCode(codeId);
                break;
            }
        }
        return "redirect:/my_account/surveys";
    }
    //endregion

    //region Adding Voucher
    @RequestMapping(value = "/surveys/{id}/addVoucher", method = RequestMethod.GET)
    public String voucher(@PathVariable("id") int surveyId, Model model) {
        if(companySurveyService.getSurveyById(surveyId)==null){
            return "error.html";
        }
        model.addAttribute("surveyId", surveyId);
        model.addAttribute("discountType", DiscountType.values());
        model.addAttribute("voucherForm",new VoucherForm());

        return "my_account/vouchers/add_voucher";
    }

    @RequestMapping(value = "/surveys/{id}/addVoucher", method = RequestMethod.POST)
    public String addVouchersForm(Model model,@PathVariable("id") int surveyId, @AuthenticationPrincipal UserCompany userCompany, @ModelAttribute(name="companyForm") CompanyDetailsForm companyDetailsForm, @Validated @ModelAttribute(name="voucherForm") VoucherForm voucherForm,BindingResult bindingResult, @ModelAttribute(name="passwordForm") PasswordForm passwordForm){

        if(companySurveyService.getSurveyById(surveyId)==null){
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
    @RequestMapping("/statistics")
    public String statistics(Model model) {
        return "my_account/stats/stats_homePage";
    }
    //endregion
}


