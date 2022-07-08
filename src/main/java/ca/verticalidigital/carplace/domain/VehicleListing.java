package ca.verticalidigital.carplace.domain;

import ca.verticalidigital.carplace.domain.enumeration.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A VehicleListing.
 */
@Entity
@Table(name = "vehicle_listing")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class VehicleListing implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "price")
    private Integer price;

    @Column(name = "year")
    private Integer year;

    @Column(name = "mileage")
    private Integer mileage;

    @Enumerated(EnumType.STRING)
    @Column(name = "fuel")
    private FuelType fuel;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ListingStatus status;

    @NotNull
    @Size(max = 40)
    @Column(name="internal_number", length = 40)
    private String internalNumber;

    @Column(name = "performance")
    private Integer performance;

    @Column(name = "mot")
    private Instant mot;

    @Column(name = "reg_date")
    private Instant regDate;

    @NotNull
    @Column(name = "vat")
    private Boolean vat;

    @Column(name = "vin")
    private String vin;

    @Size(max = 32)
    @Column(name = "colour", length = 32)
    private String colour;

    @Enumerated(EnumType.STRING)
    @Column(name = "ac")
    private Ac ac;

    @Column(name = "esp")
    private Boolean esp;

    @Column(name = "abs")
    private Boolean abs;

    @Column(name = "doors")
    private Integer doors;

    @Column(name = "cubic_capacity")
    private Integer cubicCapacity;

    @Column(name = "number_of_seats")
    private Integer numberOfSeats;

    @Enumerated(EnumType.STRING)
    @Column(name = "emission_class")
    private EmissionClass emissionClass;

    @Column(name = "emission")
    private Integer emission;

    @Enumerated(EnumType.STRING)
    @Column(name = "gearbox")
    private Gearbox gearbox;

    @ManyToOne
    @JsonIgnoreProperties(value = {"vehicleListings", "categories"}, allowSetters = true)
    private CarModel carModel;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public VehicleListing id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPrice() {
        return this.price;
    }

    public VehicleListing price(Integer price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getYear() {
        return this.year;
    }

    public VehicleListing year(Integer year) {
        this.setYear(year);
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMileage() {
        return this.mileage;
    }

    public VehicleListing mileage(Integer mileage) {
        this.setMileage(mileage);
        return this;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public FuelType getFuel() {
        return this.fuel;
    }

    public VehicleListing fuel(FuelType fuel) {
        this.setFuel(fuel);
        return this;
    }

    public void setFuel(FuelType fuel) {
        this.fuel = fuel;
    }

    public ListingStatus getStatus() {
        return this.status;
    }

    public VehicleListing status(ListingStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(ListingStatus status) {
        this.status = status;
    }

    public CarModel getCarModel() {
        return this.carModel;
    }

    public void setCarModel(CarModel carModel) {
        this.carModel = carModel;
    }

    public VehicleListing carModel(CarModel carModel) {
        this.setCarModel(carModel);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VehicleListing)) {
            return false;
        }
        return id != null && id.equals(((VehicleListing) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VehicleListing{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", year=" + getYear() +
            ", mileage=" + getMileage() +
            ", fuel='" + getFuel() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
