package ca.verticalidigital.carplace.service.dto;
import ca.verticalidigital.carplace.domain.Dealer;

import java.io.Serializable;
import java.util.Objects;

public class DealerDTO implements Serializable {

    private Long id;
    private String dealerName;
    private String city;
    private String address;
    private String contactPhone;

    public DealerDTO() {
    }

    public DealerDTO(Dealer dealer) {
        this.id = dealer.getId();
        this.dealerName = dealer.getDealerName();
        this.city = dealer.getCity();
        this.address = dealer.getAddress();
        this.contactPhone = dealer.getContactPhone();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o){
            return true;
        }
        if(!(o instanceof DealerDTO)){
            return false;
        }
        DealerDTO dealerDTO = (DealerDTO) o;
        if(this.id == null){
            return false;
        }
        return Objects.equals(this.id, dealerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
