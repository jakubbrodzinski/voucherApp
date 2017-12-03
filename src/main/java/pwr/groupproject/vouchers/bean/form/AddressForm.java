package pwr.groupproject.vouchers.bean.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class AddressForm {
    @NotBlank
    @Pattern(regexp = "\\d\\d-\\d\\d\\d")
    private String postalCode;
    @NotBlank
    @Pattern(regexp = "\\p{L}*")
    private String city;
    @NotBlank
    @Pattern(regexp = "[-A-Za-z0-9 ,.]*")
    private String addressDetails;

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
}
