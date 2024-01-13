package com.example.demo1;

import com.example.demo1.Domain.Car;
import com.example.demo1.Domain.Rent;
import com.example.demo1.Repository.CarDbRepository;
import com.example.demo1.Repository.RentDbRepository;
import com.example.demo1.Repository.Repository;
import com.example.demo1.Service.CarService;
import com.example.demo1.Service.RentService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private Repository<Car> carRepository = new CarDbRepository("carsDatabase.db");
    private CarService carService = new CarService(carRepository);
    private Repository<Rent> rentRepository = new RentDbRepository("rentDatabase.db");
    private RentService rentService = new RentService(rentRepository);
    Stage primaryStage = new Stage();
    @Override
    public void start(Stage stage) throws IOException {

        HelloController hc = new HelloController(carService, rentService, primaryStage);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        fxmlLoader.setController(hc);
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        stage.setTitle("Car renting!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}