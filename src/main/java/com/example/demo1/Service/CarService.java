package com.example.demo1.Service;


import com.example.demo1.Domain.Car;
import com.example.demo1.Exception.CarException;
import com.example.demo1.Repository.RepositoryInterface;

import java.io.IOException;
import java.util.List;
public class CarService {
    RepositoryInterface<Car> carRepository;

    public CarService(RepositoryInterface<Car> carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getAllCars() {
        return carRepository.getAll();
    }

    public void addCar(int id, String brand, String model) throws CarException, IOException {
        if (id < 0) {
            throw new CarException("Invalid id, it can't be negative");
        }
        for(Car car: getAllCars()) {
            if(car.getId() == id) {
                throw new CarException("It already exists a car with this id: " + id);
            }
        }
        Car car = new Car(id, brand, model);
        carRepository.add(car);
    }

    public Car getCarById(int id) throws CarException {
        Car car = carRepository.getById(id);
        if(car == null) {
            throw new CarException("Couldn't find car with ID: " + id);
        }
        return car;
    }

    public void updateCar(int id, String newBrand, String newModel) throws CarException, IOException {
        Car car = carRepository.getById(id);
        if(car == null) {
            throw new CarException("Couldn't find car with ID: " + id);
        }

        car = new Car(id, newBrand, newModel);
        carRepository.update(car);
//        car.setBrand(newBrand);
//        car.setModel(newModel);
    }

    public void deleteCar(int id) throws CarException, IOException {
        Car car = carRepository.getById(id);
        if(car == null) {
            throw new CarException("Couldn't find car with ID: " + id);
        }

        carRepository.delete(car);
    }
}
