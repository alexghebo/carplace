package ca.verticalidigital.carplace.service.dto;

import ca.verticalidigital.carplace.domain.Dealer;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class DealerDTO extends UserDTO{
    @NotNull
    private String dealerName;

    @NotNull
    private String city;

    @NotNull
    private String address;

    @NotNull
    @Size(min = 10, max = 10)
    private Integer contactPhone;

    public DealerDTO(){
    }

    public DealerDTO(Dealer dealer) {
        this.dealerName = dealer.getDealerName();
        this.city = dealer.getCity();
        this.address = dealer.getAddress();
        this.contactPhone = dealer.getContactPhone();
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(Integer contactPhone) {
        this.contactPhone = contactPhone;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Dealer{" +
            "dealerName='" + dealerName + '\'' +
            ", city='" + city + '\'' +
            ", address='" + address + '\'' +
            ", contactPhone='" + contactPhone + '\'' +
            "}";
    }
}

