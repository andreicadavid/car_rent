package com.example.demo1;


import com.example.demo1.Domain.*;
import com.example.demo1.Repository.CarDbRepository;
import com.example.demo1.Repository.RentDbRepository;
import com.example.demo1.Repository.Repository;
import com.example.demo1.Repository.RepositoryInterface;
import com.example.demo1.Service.CarService;
import com.example.demo1.Service.RentService;
import com.example.demo1.UI.CarUI;
import com.example.demo1.UI.MainUI;
import com.example.demo1.UI.RentUI;

import java.io.IOException;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        Repository<Car> carRepository = new Repository<Car>();
//        RepositoryInterface<Car> carRepository = new FileRepository<>("cars.txt", new CarFactory());
//        RepositoryInterface<Rent> rentRepository = new FileRepository<>("rents.txt", new RentFactory());
//        carRepository.add(new Car(1, "BMW", "M3"));
//        carRepository.add(new Car(2, "Mercedes-Benz", "C63 AMG"));
//        carRepository.add(new Car(3, "Audi", "RS7"));
//        carRepository.add(new Car(4, "Jaguar", "F-Type"));
//        carRepository.add(new Car(5, "Lexus", "LC 500"));

//        Car car1 = new Car(1, "BMW", "M3");
//        Car car2 = new Car(2, "Mercedes-Benz", "C63 AMG");
//        Car car3 = new Car(3, "Audi", "RS7");
//        Car car4 = new Car(4, "Jaguar", "F-Type");
//        Car car5 = new Car(5, "Lexus", "LC 500");
//
//        Repository<Rent> rentRepository = new Repository<Rent>();
//        rentRepository.add(new Rent(1, car1, "2023-06-10", "2023-06-15"));
//        rentRepository.add(new Rent(2, car2, "2023-07-05", "2023-07-10"));
//        rentRepository.add(new Rent(3, car3, "2023-08-20", "2023-08-25"));
//        rentRepository.add(new Rent(4, car4, "2023-09-15", "2023-09-20"));
//        rentRepository.add(new Rent(5, car5, "2023-10-10", "2023-10-15"));
        IEntityFactory<Car> entityFactoryCar = new CarFactory();
        IEntityFactory<Rent> entityFactoryRent = new RentFactory();
        RepositoryInterface<Car> carRepository = null;
        RepositoryInterface<Rent> rentRepository = null;
        Settings setari = Settings.getInstance();


        try {
            if (Objects.equals(setari.getRepoType(), "memory")) {
                carRepository = new Repository<Car>();
                rentRepository = new Repository<Rent>();
            } else if (Objects.equals(setari.getRepoType(), "db")) {
                 carRepository = new CarDbRepository(setari.getRepoFileCar());
                 rentRepository = new RentDbRepository(setari.getRepoFileRent());
            } else {
                System.out.println("Warning: Invalid repo_type " + setari.getRepoType()+ ". Using memory repository by default. Valid repo types are: memory, text, binary.");
                carRepository = new Repository<Car>();
                rentRepository = new Repository<Rent>();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println(setari.getRepoType());
        CarService carService = new CarService(carRepository);
        CarUI carUI = new CarUI(carService);

        RentService rentService = new RentService(rentRepository);
        RentUI rentUI = new RentUI(rentService, carService);

        MainUI mainUI = new MainUI(carUI, rentUI);
        mainUI.runMenu();



         if (Objects.equals(setari.getRepoType(), "db")) {
              if (carRepository instanceof CarDbRepository) {
                   ((CarDbRepository) carRepository).closeConnection();
              }
              if (rentRepository instanceof RentDbRepository) {
                   ((RentDbRepository) rentRepository).closeConnection();
              }
         }

//        RentDbRepository rentRepository = new RentDbRepository("rentsDatabase.db");
//        rentRepository.openConnection();
//        rentRepository.createTable();
        //rentRepository.initTable();
        //rentRepository.closeConnection();
    }
}