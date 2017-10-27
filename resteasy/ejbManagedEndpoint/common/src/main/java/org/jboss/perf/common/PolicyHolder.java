/*
 * SPECjEnterprise2018 - a benchmark for enterprise middleware
 * Copyright 1995-2018 Standard Performance Evaluation Corporation
 * All Rights Reserved
 */
package org.jboss.perf.common;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@XmlRootElement
@Table(name = "POLICYHOLDER", uniqueConstraints = @UniqueConstraint(columnNames = "email"),
        indexes = { @Index(columnList = "email", name="I_POLICYHOLDER_EMAIL") })
public class PolicyHolder implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(length = 25, nullable=false)
    @Size(min = 1, max = 25)
    @Pattern(regexp = "[A-Za-z ]*", message = "must contain only letters and spaces")
    private String firstName;

    @NotNull
    @Column(length = 25, nullable=false)
    @Size(min = 1, max = 25)
    @Pattern(regexp = "[A-Za-z ]*", message = "must contain only letters and spaces")
    private String lastName;

    @Column(length = 25)
    @Size(min = 1, max = 25)
    @Pattern(regexp = "[A-Za-z ]*", message = "must contain only letters and spaces")
    private String middleName;

    @NotNull
    @ValidEmail
    @Column(nullable=false)
    private String email;

    @NotNull
    @Column(nullable=false)
    private String password;

    @NotNull
    @Column(nullable=false)
    private Gender gender;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(nullable=false)
    private Date birthDate;

    private int movingViolations;

    private int claims;

    private int accidents;

    @NotNull
    @Valid
    private Address address;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="POLICY_HOLDER_ID")
    private List<Vehicle> vehicles;

    private int preloadIndex = -1;

    public PolicyHolder() {
        address = new Address();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public int getMovingViolations() {
        return movingViolations;
    }

    public void setMovingViolations(int movingViolations) {
        this.movingViolations = movingViolations;
    }

    public int getClaims() {
        return claims;
    }

    public void setClaims(int claims) {
        this.claims = claims;
    }

    public int getAccidents() {
        return accidents;
    }

    public void setAccidents(int accidents) {
        this.accidents = accidents;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Vehicle> getVehicles() {
        if(vehicles == null)
            vehicles = new ArrayList<>();
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public void addVehicle(Vehicle vehicle) {
        if(vehicles == null)
            vehicles = new ArrayList<>();
        vehicles.add(vehicle);
    }

    public void updateVehicle(Vehicle newVehicle) {
        if(vehicles != null) {
            for(int i=0; i < vehicles.size();i++) {
                if(vehicles.get(i).getVin().equals(newVehicle.getVin())) {
                    vehicles.set(i, newVehicle);
                    return;
                }
            }
        }
    }

    public Vehicle getVehicleByVin(String vin) {
        if (vehicles != null) {
            for(Vehicle vehicle : vehicles)
                if(vehicle.getVin().equals(vin))
                    return vehicle;
        }

        return null;
    }

    public void setPreloadIndex(int index) {
        preloadIndex = index;
    }

    public int getPreloadIndex() {
        return preloadIndex;
    }

    @Override
    public String toString() {
        return "PolicyHolder{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", gender=" + gender +
                ", birthDate=" + birthDate +
                ", movingViolations=" + movingViolations +
                ", claims=" + claims +
                ", accidents=" + accidents +
                ", address=" + address +
                ", vehicles=" + vehicles +
                '}';
    }


}
