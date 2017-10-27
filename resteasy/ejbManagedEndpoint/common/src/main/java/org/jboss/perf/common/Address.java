/*
 * SPECjEnterprise2018 - a benchmark for enterprise middleware
 * Copyright 1995-2018 Standard Performance Evaluation Corporation
 * All Rights Reserved
 */

package org.jboss.perf.common;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;


/**
 * Typical Address used commonly across domains.
 *
 */
@Embeddable
@SuppressWarnings("serial")
public class Address implements Serializable {

    @NotNull
//    @Size(min = 1, max = 30)
    @Column(length = 30, nullable=false)
    private String street1;

//    @Size(min = 0, max = 25)
    @Column(length = 25)
    private String street2;

    @NotNull
//    @Size(min = 1, max = 30)
    @Column(length = 30, nullable=false)
    @Pattern(regexp = "[A-Za-z-'.() ]*", message = "must contain only letters, spaces, dash, apostrope, dot or parentheses")
    private String city;

    @NotNull
//    @Size(min = 2, max = 25)
    @Column(length = 25, nullable=false)
    @Pattern(regexp = "[A-Za-z ]*", message = "must contain only letters and spaces")
    private String state;

    @NotNull
//    @Size(min = 3, max = 25)
    @Column(length = 25, nullable=false)
    @Pattern(regexp = "[A-Za-z ]*", message = "must contain only letters and spaces")
    private String country;

    @NotNull
//    @Size(min = 3, max = 25)
    @Column(length = 25, nullable=false)
//    @Digits(fraction = 0, integer = 8)
    private String zip;

    @NotNull
//    @Size(min = 8, max = 12)
    @Column(length = 12, nullable=false)
//    @Digits(fraction = 0, integer = 12)
    private String phone;

    public Address() {
    }

    public Address(String street1, String street2, String city, String state,
                   String country, String zip, String phone) {
        this.street1 = street1;
        this.street2 = street2;
        this.city    = city;
        this.state   = state;
        this.country = country;
        setZip(zip);
        setPhone(phone);
    }

    public String toString() {
        return street1 + "\n" + street2 + "\n" + city + "," + state + " " + zip + "\n" + phone;
    }

    public String getStreet1() {
        return street1;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
