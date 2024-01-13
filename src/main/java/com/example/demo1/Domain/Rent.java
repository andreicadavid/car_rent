package com.example.demo1.Domain;

import java.io.Serializable;

public class Rent extends Entity implements Serializable {
    private static final long serialVersionUID = 100L;
    private Car car;
    private String startDate;
    private String endDate;

    public Rent( int id, Car car, String startDate, String endDate) {
        super(id);
        this.car = car;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Id: " + id + "\n" + car.toSimpleString() + "\n" + "StartDate: " + startDate + "\n" + "EndDate: " + endDate + "\n";
    }

    public Car getCar() {
        return car;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setCar(Car newCar) {
        car = newCar;
    }

    public void setStartDate(String newStartDate) {
        startDate = newStartDate;
    }

    public void setEndDate(String newEndDate) {
        endDate = newEndDate;
    }
}
