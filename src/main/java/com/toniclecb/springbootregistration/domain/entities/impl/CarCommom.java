package com.toniclecb.springbootregistration.domain.entities.impl;

import com.toniclecb.springbootregistration.domain.entities.Car;

public class CarCommom implements Car {
    private long id;
    private String name, brand;

    public CarCommom(long id, String name, String brand) {
        this.id = id;
        this.name = name;
        this.brand = brand;
      }
    
      @Override
      public String toString() {
        return String.format(
            "CarCommom[id=%d, name='%s', brand='%s']",
            id, name, brand);
      }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getBrand() {
        return this.brand;
    }

}
