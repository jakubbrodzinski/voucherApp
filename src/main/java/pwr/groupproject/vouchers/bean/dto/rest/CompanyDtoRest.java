package pwr.groupproject.vouchers.bean.dto.rest;

import pwr.groupproject.vouchers.bean.model.Address;
import pwr.groupproject.vouchers.bean.model.Company;

import java.io.Serializable;

public class CompanyDtoRest implements Serializable {
    private static final long serialVersionUID = 2363445322855782907L;
    private int Id;
    private String postalCode;
    private String city;
    private String addressDetails;
    private String companyName;

    public CompanyDtoRest() {
    }

    public CompanyDtoRest(Company company) {
        this.Id = company.getId();
        this.companyName = company.getCompanyName();
        Address address = company.getCompanyAddress();
        this.city = address.getCity();
        this.addressDetails = address.getAddressDetails();
        this.postalCode = address.getPostalCode();
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
