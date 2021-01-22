package com.shutl.model;

import javax.validation.constraints.Pattern;

public class Quote {
    @Pattern(regexp="^[a-zA-Z]{1,2}\\d[a-zA-Z\\d]?\\s*\\d[a-zA-Z]{2}$",message="Enter a valid UK Postcode")
    private String pickupPostcode;

    @Pattern(regexp="^[a-zA-Z]{1,2}\\d[a-zA-Z\\d]?\\s*\\d[a-zA-Z]{2}$",message="Enter a valid UK Postcode")
    private String deliveryPostcode;

    private Vehicle vehicle;
    private Long price;

    public Quote() {}

    public Quote(String pickupPostcode, String deliveryPostcode, Vehicle vehicle) {
        this.pickupPostcode = pickupPostcode.replaceAll("\\s", "");
        this.deliveryPostcode = deliveryPostcode.replaceAll("\\s", "");
        this.vehicle = vehicle;
    }

    public Quote(String pickupPostcode, String deliveryPostcode, Vehicle vehicle, Long price) {
        this(pickupPostcode, deliveryPostcode, vehicle);
        this.price = price;
    }

    public String getPickupPostcode() {
        return pickupPostcode;
    }

    public void setPickupPostcode(String pickupPostcode) {
        this.pickupPostcode = pickupPostcode.replaceAll("\\s", "");
    }

    public String getDeliveryPostcode() {
        return deliveryPostcode;
    }

    public void setDeliveryPostcode(String deliveryPostcode) {
        this.deliveryPostcode = deliveryPostcode.replaceAll("\\s", "");
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
