package com.example.demo1.Repository;


import com.example.demo1.Domain.Car;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.ArrayList;

public class CarDbRepository extends Repository<Car> implements IDbRepository<Car> {
    private String JDBC_URL;

    private Connection connection = null;


    public CarDbRepository(String JDBC_URL){
        this.JDBC_URL = "jdbc:sqlite:" + JDBC_URL;
        openConnection();
        createTable();
//        initTable();
    }

    public void openConnection() {
        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl(JDBC_URL);

        try {
            if(connection == null || connection.isClosed()){
                connection = ds.getConnection();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
//            e.printStackTrace();
        }
    }

    public void closeConnection() {
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
//                e.printStackTrace();
            }
        }
    }

    public void createTable() {
        try(final Statement stat = connection.createStatement()) {
            stat.execute("CREATE TABLE IF NOT EXISTS cars(id int, brand varchar(400), model varchar(400));");
        } catch (SQLException e) {
//                throw new RuntimeException(e);
            e.printStackTrace();
        }
    }

    public void initTable() {
        try (PreparedStatement stat = connection.prepareStatement("INSERT INTO cars VALUES (?,?,?);")) {
            for (int i = 1; i <= 100; i++) {
                String brand = generateRandomBrand();
                String model = generateRandomModelForBrand(brand);
                stat.setInt(1, i);
                stat.setString(2, brand);
                stat.setString(3, model);
                stat.executeUpdate();
            }
            System.out.println("Generated and inserted 100 random Car entities into the database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String generateRandomBrand() {
        String[] brands = {"BMW", "Audi", "Mercedes"};
        return brands[(int) (Math.random() * brands.length)];
    }

    private String generateRandomModelForBrand(String brand) {
        switch (brand) {
            case "BMW":
                return generateRandomModelForBMW();
            case "Audi":
                return generateRandomModelForAudi();
            case "Mercedes":
                return generateRandomModelForMercedes();
            default:
                return "Unknown Model";
        }
    }

    private String generateRandomModelForBMW() {
        String[] bmwModels = {"M6", "X5", "3 Series"};
        return bmwModels[(int) (Math.random() * bmwModels.length)];
    }

    private String generateRandomModelForAudi() {
        String[] audiModels = {"A3", "Q5", "A6"};
        return audiModels[(int) (Math.random() * audiModels.length)];
    }
    private String generateRandomModelForMercedes() {
        String[] mercedesModels = {"GLE", "C-Class", "S-Class"};
        return mercedesModels[(int) (Math.random() * mercedesModels.length)];
    }

    @Override
    public ArrayList<Car> getAll(){
        ArrayList<Car> cars = new ArrayList<>();
        try(PreparedStatement stat = connection.prepareStatement("SELECT * FROM cars")){
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                Car c = new Car(rs.getInt(1), rs.getString(2), rs.getString(3));
                cars.add(c);
            }
        } catch (SQLException e) {
//                throw new RuntimeException(e);
            e.printStackTrace();
        }

        return cars;
    }

    @Override
    public void add(Car c){
        try(PreparedStatement stat = connection.prepareStatement("INSERT INTO cars VALUES (?,?,?);")){
                stat.setInt(1,c.getId());
                stat.setString(2,c.getBrand());
                stat.setString(3,c.getModel());
                stat.executeUpdate();
        } catch (SQLException e) {
//                throw new RuntimeException(e);
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Car car) {
        String sql = "DELETE FROM cars WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, car.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void update(Car c){
        try(PreparedStatement stmt = connection.prepareStatement("UPDATE cars SET brand = ? , model = ? WHERE id = ?")){
            stmt.setInt(3,c.getId());
            stmt.setString(1, c.getBrand());
            stmt.setString(2, c.getModel());

            stmt.executeUpdate();
            System.out.println("Executing SQL query: " + stmt.toString());


        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}

