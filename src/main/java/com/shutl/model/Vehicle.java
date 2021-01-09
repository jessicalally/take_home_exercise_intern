package com.shutl.model;

public enum Vehicle {
  DEFAULT(1.0),
  BICYCLE(1.1),
  MOTORBIKE(1.15),
  PARCEL_CAR(1.2),
  SMALL_VAN(1.3),
  LARGE_VAN(1.4);

  private final Double markup;

  Vehicle(Double markup) {
    this.markup = markup;
  }

  public Double getMarkup() {
    return markup;
  }
}
