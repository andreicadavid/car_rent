package com.example.demo1;

import com.example.demo1.Domain.Car;
import com.example.demo1.Domain.Rent;
import com.example.demo1.Exception.CarException;
import com.example.demo1.Service.CarService;
import com.example.demo1.Service.RentService;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public class HelloController {

    CarService carService;
    RentService rentService;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public HelloController(CarService carService ,RentService rentService, Stage stage) {
        this.carService = carService;
        this.rentService = rentService;
        this.stage = stage;
    }

    @FXML
    private ListView<Car> carListView;

    @FXML
    private ListView<Rent> rentListView;

    @FXML
    private TextField idTextField;

    @FXML
    private TextField idTextFieldRent;

    @FXML
    private TextField brandTextField;

    @FXML
    private TextField modelTextField;

    @FXML
    private TextField IdCarTextFieldRent;

    @FXML
    private TextField brandTextFieldRent;

    @FXML
    private TextField modelTextFieldRent;

    @FXML
    private TextField startDateTextField;

    @FXML
    private TextField endDateTextField;

    @FXML
    private Button addCarButton;

    @FXML
    private Button updateCarButton;

    @FXML
    private Button deleteCarButton;

    @FXML
    private Button addRentButton;

    @FXML
    private Button updateRentButton;

    @FXML
    private Button deleteRentButton;

    ObservableList<Car> cars;
    ObservableList<Rent> rents;

    public void displayCars() {
        List<Car> allCars = carService.getAllCars();
        cars = FXCollections.observableArrayList(allCars);
        carListView.setItems(cars);
        System.out.println("Number of cars: " + cars.size());
    }

    public void displayRents() {
        List<Rent> allRents = rentService.getAllRents();
        rents = FXCollections.observableArrayList(allRents);
        rentListView.setItems(rents);
        System.out.println("Number of rents: " + rents.size());
    }

    public void initialize() {
        displayCars();
        displayRents();
    }

    @FXML
    void onCarListViewClicked() {
        Car car = carListView.getSelectionModel().getSelectedItem();
        idTextField.setText(Integer.toString(car.getId()));
        brandTextField.setText(car.getBrand());
        modelTextField.setText(car.getModel());
    }

    @FXML
    void onRentListViewClicked() {
        Rent rent = rentListView.getSelectionModel().getSelectedItem();
        idTextFieldRent.setText(Integer.toString(rent.getId()));
        IdCarTextFieldRent.setText(Integer.toString(rent.getCar().getId()));
        brandTextFieldRent.setText(rent.getCar().getBrand());
        modelTextFieldRent.setText(rent.getCar().getModel());
        startDateTextField.setText(rent.getStartDate());
        endDateTextField.setText(rent.getEndDate());
    }

    @FXML
    private void onAddBtnClicked() {
        try {
            int id = Integer.parseInt(idTextField.getText());
            String brand = brandTextField.getText();
            String model = modelTextField.getText();
            carService.addCar(id, brand, model);
            System.out.println("Car added successfully!");
            displayCars();
        }catch (Exception e) {
            Alert errorPopUp = new Alert(Alert.AlertType.ERROR);
            errorPopUp.setTitle("Error");
            errorPopUp.setContentText(e.getMessage());
            errorPopUp.show();
        }
    }
    @FXML
    private void onUpdateBtnClicked() {
        try {
            int id = Integer.parseInt(idTextField.getText());
            String brand = brandTextField.getText();
            String model = modelTextField.getText();
            carService.updateCar(id, brand, model);
            displayCars();
        } catch (Exception e) {
            Alert errorPopUp = new Alert(Alert.AlertType.ERROR);
            errorPopUp.setTitle("Error");
            errorPopUp.setContentText(e.getMessage());
            errorPopUp.show();
        }
    }

    @FXML
    private void onDeleteBtnClicked() {
        try {
            int id = Integer.parseInt(idTextField.getText());
            carService.deleteCar(id);
            displayCars();
        } catch (Exception e) {
            Alert errorPopUp = new Alert(Alert.AlertType.ERROR);
            errorPopUp.setTitle("Error");
            errorPopUp.setContentText(e.getMessage());
            errorPopUp.show();
        }
    }

    @FXML
    private void onAddBtnRentClicked() {
        try {
            int id = Integer.parseInt(idTextFieldRent.getText());
            int idCar = Integer.parseInt(IdCarTextFieldRent.getText());
            String brand = brandTextFieldRent.getText();
            String model = modelTextFieldRent.getText();
            Car car = new Car(idCar,brand,model);
            String startDate = startDateTextField.getText();
            String endDate = endDateTextField.getText();

            rentService.addRent(id, car, startDate, endDate);
            System.out.println("Rent added successfully!");
            displayRents();
        }catch (Exception e) {
            Alert errorPopUp = new Alert(Alert.AlertType.ERROR);
            errorPopUp.setTitle("Error");
            errorPopUp.setContentText(e.getMessage());
            errorPopUp.show();
        }
    }

    @FXML
    private void onUpdateBtnRentClicked() {
        try {
            int id = Integer.parseInt(idTextFieldRent.getText());
            String startDate = startDateTextField.getText();
            String endDate = endDateTextField.getText();

            rentService.updateRent(id, startDate, endDate);
            displayRents();
        } catch (Exception e) {
            Alert errorPopUp = new Alert(Alert.AlertType.ERROR);
            errorPopUp.setTitle("Error");
            errorPopUp.setContentText(e.getMessage());
            errorPopUp.show();
        }
    }

    @FXML
    private void onDeleteBtnRentClicked() {
        try {
            int id = Integer.parseInt(idTextFieldRent.getText());
            rentService.deleteRent(id);
            displayRents();
        } catch (Exception e) {
            Alert errorPopUp = new Alert(Alert.AlertType.ERROR);
            errorPopUp.setTitle("Error");
            errorPopUp.setContentText(e.getMessage());
            errorPopUp.show();
        }
    }

    @FXML
    public void switchToRents(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("rents.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void switchToCars(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}