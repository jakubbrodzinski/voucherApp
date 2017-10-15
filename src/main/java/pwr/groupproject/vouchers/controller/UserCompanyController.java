package pwr.groupproject.vouchers.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(UserCompanyController.ROOT_MAPPING)
@PreAuthorize("hasRole('USER')")
public class UserCompanyController {
    public static final String ROOT_MAPPING="/my_account";

    @RequestMapping("/home")
    public String homePage(Model model){
        return "my_account/home_page.html";
    }
}
