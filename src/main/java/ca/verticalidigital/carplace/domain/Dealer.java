package ca.verticalidigital.carplace.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Dealer extends User{
    @NotNull
    @Column(name = "dealer_name")
    private String dealerName;

    @NotNull
    @Column(name = "city")
    private String city;

    @NotNull
    @Column(name = "address")
    private String address;

    @NotNull
    @Size(min = 10, max = 10)
    @Column(name = "contact_phone")
    private Integer contactPhone;

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
        return "User{" +
            "login='" + super.getLogin() + '\'' +
            ", firstName='" + super.getFirstName() + '\'' +
            ", lastName='" + super.getLastName() + '\'' +
            ", email='" + super.getEmail() + '\'' +
            ", imageUrl='" + super.getImageUrl() + '\'' +
            ", activated='" + super.isActivated() + '\'' +
            ", langKey='" + super.getLangKey() + '\'' +
            ", activationKey='" + super.getActivationKey() + '\'' +
            ", dealerName='" + dealerName + '\'' +
            ", city='" + city + '\'' +
            ", address='" + address + '\'' +
            ", contactPhone='" + contactPhone + '\'' +
            "}";
    }

}
