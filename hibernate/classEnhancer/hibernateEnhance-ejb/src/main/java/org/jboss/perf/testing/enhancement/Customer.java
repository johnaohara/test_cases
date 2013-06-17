package org.jboss.perf.testing.enhancement;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity
@Table(name="O_CUSTOMER")
public class Customer implements Serializable {

    @Id
    @Column(name="C_ID")
    private int id;

    @Column(name="C_FIRST")
    private String firstName;

    @Column(name="C_LAST")
    private String lastName;

    @Embedded
    @AttributeOverrides(
            {@AttributeOverride(name="street1",column=@Column(name="C_STREET1")),
                    @AttributeOverride(name="street2",column=@Column(name="C_STREET2")),
                    @AttributeOverride(name="city",   column=@Column(name="C_CITY")),
                    @AttributeOverride(name="state",  column=@Column(name="C_STATE")),
                    @AttributeOverride(name="country",column=@Column(name="C_COUNTRY")),
                    @AttributeOverride(name="zip",    column=@Column(name="C_ZIP")),
                    @AttributeOverride(name="phone",  column=@Column(name="C_PHONE"))})
    private Address       address;

    @Version
    @Column(name = "C_VERSION")
    private int version;

    protected Customer() {
    }

    public Customer(String first, String last, Address address) {

        this.firstName   = first;
        this.lastName    = last;
        this.address     = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer customerId) {
        this.id = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        return id == ((Customer) o).id;
    }

    @Override
    public int hashCode() {
        return new Integer(id).hashCode();
    }

    @Override
    public String toString() {
        return this.getFirstName() + " " + this.getLastName();
    }
}