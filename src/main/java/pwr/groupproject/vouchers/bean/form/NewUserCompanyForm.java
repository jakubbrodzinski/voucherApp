package pwr.groupproject.vouchers.bean.form;

import org.hibernate.validator.constraints.Length;
import pwr.groupproject.vouchers.bean.form.annotations.PasswordValidationConstraint;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@PasswordValidationConstraint(filedOne = "password",filedTwo = "repeatedPassword",groups = NewUserCompanyForm.ValidationGroup1.class)
public class NewUserCompanyForm implements Serializable {
    private static final long serialVersionUID = 6414340627544822204L;

    @Email(groups = ValidationGroup1.class)
    @NotBlank(groups = ValidationGroup1.class)
    private String userName;
    @NotBlank(groups = ValidationGroup1.class)
    @Length(min=6)
    private String password;
    @NotBlank(groups = ValidationGroup1.class)
    private String repeatedPassword;
    @NotBlank(groups = ValidationGroup1.class)
    @Pattern(regexp = "[\\pL0-9a-zA-Z/ \\-.]*",message = "{constraint.company.name}",groups = ValidationGroup1.class)
    private String companyName;

    @Pattern(regexp = "\\d\\d-\\d\\d\\d", groups = ValidationGroup2.class)
    private String postalCode;
    @Pattern(regexp = "[\\p{L}|\\pL]*", groups = ValidationGroup2.class)
    private String city;
    @Pattern(regexp = "[\\pL-A-Za-z0-9\\-/ ,.]*", groups = ValidationGroup2.class)
    private String addressDetails;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddressDetails() {
        return addressDetails;
    }

    public void setAddressDetails(String addressDetails) {
        this.addressDetails = addressDetails;
    }

    public interface ValidationGroup1 {
    }

    public interface ValidationGroup2 {
    }
}
