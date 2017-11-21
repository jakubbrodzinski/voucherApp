package pwr.groupproject.vouchers.bean.form;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

public class CompanyDetailsForm {
    @NotBlank
    @Pattern(regexp = "[0-9a-zA-Z -.]",message = "{constraint.company.name}")
    private String companyName;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
