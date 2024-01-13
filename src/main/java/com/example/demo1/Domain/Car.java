package com.example.demo1.Domain;
import java.io.Serializable;
import java.util.Objects;

public class Car extends Entity implements Serializable{
    private static final long serialVersionUID = 100L;
    private String brand;
    private String model;

    public Car(int id, String brand, String model) {
        super(id);
        this.brand = brand;
        this.model = model;
    }
    public Car() {
        super(0);
        this.brand = "";
        this.model = "";
    }

    @Override
    public String toString() {
        return "Id: " + id + "\n" + "Brand:  " + brand + "\n" + "Model: " + model + "\n";
    }

    public String toSimpleString() {
        return  "Car: " + id + " "+
                brand + " "+
                model;
    }

    public String toSimpleString2() {
        return  id + "," +
                brand + "," +
                model;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public void setBrand(String newBrand) {
        brand = newBrand;
    }

    public void setModel(String newModel) {
        model = newModel;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Car otherCar = (Car) obj;
        return id == otherCar.id && brand.equals(otherCar.brand) && model.equals(otherCar.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, model);
    }
}
