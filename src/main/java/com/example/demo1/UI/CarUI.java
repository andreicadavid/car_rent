package com.example.demo1.UI;


import com.example.demo1.Domain.Car;
import com.example.demo1.Exception.CarException;
import com.example.demo1.Service.CarService;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;


public class CarUI {
    private CarService carService;
    private Scanner scanner;

    public CarUI(CarService carService) {
        this.carService = carService;
        this.scanner = new Scanner(System.in);
    }

    public void runMenu() throws IOException {
        while (true) {
            System.out.println("Menu");
            System.out.println("1. Add car");
            System.out.println("2. Show all cars");
            System.out.println("3. Find car by ID");
            System.out.println("4. Update car");
            System.out.println("5. Delete car");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    addCar();
                    break;
                case 2:
                    showAllCars();
                    break;
                case 3:
                    findCarById();
                    break;
                case 4:
                    updateCar();
                    break;
                case 5:
                    deleteCar();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid option... try again!");
            }
        }
    }

    public void addCar() throws IOException {
        System.out.println("Enter Car ID: ");
        int id = scanner.nextInt();
        System.out.println("Enter Car Brand: ");
        String brand = scanner.next();
        System.out.println("Enter Car Model: ");
        String model = scanner.next();

        try {
            carService.addCar(id, brand, model);
            System.out.println("Car added successfully!");
        } catch (CarException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void showAllCars() {
        List<Car> cars = carService.getAllCars();
        for (Car car : cars) {
            System.out.println(car.toString());
        }
    }

    public void findCarById() {
        System.out.println("Enter Car ID: ");
        int id = scanner.nextInt();
        try {
            Car car = carService.getCarById(id);
            System.out.println(car.toString());
        } catch (CarException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void updateCar() throws IOException {
        System.out.println("Enter Car ID: ");
        int id = scanner.nextInt();
        System.out.print("Enter New Car Brand: ");
        String newBrand = scanner.next();
        System.out.print("Enter New Car Model: ");
        String newModel = scanner.next();

        try {
            carService.updateCar(id, newBrand, newModel);
            System.out.println("Car updated successfully!");
        } catch (CarException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void deleteCar() throws IOException {
        System.out.println("Enter Car ID: ");
        int id = scanner.nextInt();
        try {
            carService.deleteCar(id);
            System.out.println("Car deleted successfully!");
        }
        catch (CarException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}



