package pwr.groupproject.vouchers.bean.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class CompanyDetailsForm {
    @NotBlank
    @Pattern(regexp = "[\\pL0-9a-zA-Z -.]*", message = "{constraint.company.name}")
    private String companyName;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
