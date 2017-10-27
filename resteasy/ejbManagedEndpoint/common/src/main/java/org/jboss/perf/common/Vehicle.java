package org.jboss.perf.common;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by johara on 19/10/17.
 */
public class Vehicle {

    @Id
    @NotNull
    @Column(length = 17)
    @Size(min = 17, max = 17)
    private String vin;

    @NotNull
    private String name;

    @Size(min = 1, max=20)
    private String testSize;

    public Vehicle() {
    }

    public Vehicle(String vin, String name, String testSize) {
        this.vin = vin;
        this.name = name;
        this.testSize = testSize;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTestSize() {
        return testSize;
    }

    public void setTestSize(String testSize) {
        this.testSize = testSize;
    }

    //    @Override
//    public String toString() {
//        return "Vehicle{" +
//            "id=" + id +
//            ", name=" + name +
//            '}';
//    }

}
