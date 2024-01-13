package com.example.demo1.UI;


import com.example.demo1.Domain.Car;
import com.example.demo1.Domain.Rent;
import com.example.demo1.Exception.CarException;
import com.example.demo1.Exception.RentException;
import com.example.demo1.Service.CarService;
import com.example.demo1.Service.RentService;
import javafx.util.Pair;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class RentUI {
    private RentService rentService;
    private CarService carService;
    private Scanner scanner;

    public RentUI(RentService rentService, CarService carService) {
        this.rentService = rentService;
        this.carService = carService;
        this.scanner = new Scanner(System.in);
    }

    public void runMenu() throws IOException {
        while (true) {
            System.out.println("Rent Menu");
            System.out.println("1. Add rent");
            System.out.println("2. Show All Rents");
            System.out.println("3. Find Rent by ID");
            System.out.println("4. Update Rent");
            System.out.println("5. Delete Rent");
            System.out.println("6. Most rented cars");
            System.out.println("7. Rents made for every month of the year");
            System.out.println("8. Cars with the longest rents");
            System.out.println("9. Exit");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    addRent();
                    break;
                case 2:
                    showAllRents();
                    break;
                case 3:
                    findRentById();
                    break;
                case 4:
                    updateRent();
                    break;
                case 5:
                    deleteRent();
                    break;
                case 6:
                    sortedCarStatistics();
                    break;
                case 7:
                    monthsRentsStatistics();
                    break;
                case 8:
                    daysRentStatistic();
                    break;
                case 9:
                    return;
                default:
                    System.out.println("Invalid option... try again!");
            }
        }
    }

    public void addRent() throws IOException {
        System.out.println("Enter Rent ID: ");
        int id = scanner.nextInt();
        Car car = new Car(-1, "", "");

        try {
            System.out.println("Enter Car ID: ");
            int carId = scanner.nextInt();
            car = carService.getCarById(carId);
        } catch (CarException e) {
            System.out.println("Error: " + e.getMessage());
        }

        if (car != null) {
            System.out.print("Enter Start Date: ");
            String startDate = scanner.next();
            System.out.print("Enter End Date: ");
            String endDate = scanner.next();

            if (car.getId() != -1) {
                try {
                    rentService.addRent(id, car, startDate, endDate);
                    System.out.println("Rent added successfully.");
                } catch (RentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } else {
                System.out.println("Car not found, cannot add rent.");
            }
        } else {
            System.out.println("Car not found, cannot add rent.");
        }
    }



    public void showAllRents() {
        List<Rent> rents = rentService.getAllRents();
        for(Rent rent: rents) {
            System.out.println(rent.toString());
        }
    }

    public void findRentById() {
        System.out.println("Enter Rent ID: ");
        int id = scanner.nextInt();
        try {
            Rent rent = rentService.getRentById(id);
            System.out.println(rent.toString());
        } catch (RentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void updateRent() throws IOException {
        System.out.print("Enter Rent ID: ");
        int id = scanner.nextInt();
        System.out.print("Enter New Start Date: ");
        String newStartDate = scanner.next();
        System.out.print("Enter New End Date: ");
        String newEndDate = scanner.next();

        try {
            rentService.updateRent(id, newStartDate, newEndDate);
            System.out.println("Rent updated successfully.");
        } catch (RentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void deleteRent() throws IOException {
        System.out.print("Enter Rent ID: ");
        int id = scanner.nextInt();
        try {
            rentService.deleteRent(id);
            System.out.println("Rent deleted successfully.");
        } catch (RentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void sortedCarStatistics(){
        List<Map.Entry<Pair<String, String>, Integer>> statistic = rentService.onShowMostRentedCarsButtonClick();

        System.out.println("Car Rental Statistics:");
        statistic.forEach(entry -> {
            Pair<String, String> car = entry.getKey();
            long rentalCount = entry.getValue();
            System.out.println("Car brand " + car.getKey() + ", Model: " + car.getValue() + ", Rental Count: " + rentalCount);
        });
    }

    public void monthsRentsStatistics(){
        List<Map.Entry<Pair<String, String>, Integer>> statistic = rentService.onShowNrOfRentedCarsPerMonthButtonClick();

        System.out.println("Month rent statistics: ");
        statistic.forEach(entry -> {
            Pair<String, String> month_and_year = entry.getKey();
            Integer count = entry.getValue();
            System.out.println("data: " + month_and_year.getKey() + "/" + month_and_year.getValue() + ": " + count);
        });
    }

    public void daysRentStatistic(){
        List<Map.Entry<Car, Long>> statistic = rentService.ShowLongestRentedCars();
        System.out.println("Days of rent statistics: ");
        statistic.forEach(entry -> {
            Car car = entry.getKey();
            Long days = entry.getValue();
            System.out.println("car{ marca:" + car.getBrand() + "," + "model: " + car.getModel() +"} days of renting: " + days);
        });
    }
}

