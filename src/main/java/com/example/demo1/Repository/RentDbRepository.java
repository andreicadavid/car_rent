package com.example.demo1.Repository;


import com.example.demo1.Domain.Car;
import com.example.demo1.Domain.Rent;
import org.sqlite.SQLiteDataSource;

import java.time.LocalDate;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

public class RentDbRepository extends Repository<Rent> implements IDbRepository<Rent> {
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final Random random = new Random();


    private String JDBC_URL;

    private Connection connection;


    public RentDbRepository(String JDBC_URL){
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
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
//                throw new RuntimeException(e);
                e.printStackTrace();
            }
        }
    }

    public void createTable() {
        try(final Statement stm = connection.createStatement()){
            stm.execute("CREATE TABLE IF NOT EXISTS rents(id int, id_car int, brand varchar(100), model varchar(100), data_inceput varchar(100), data_sfarsit varchar(100));");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void initTable() {
        try (PreparedStatement stat = connection.prepareStatement("INSERT INTO rents VALUES (?,?,?,?,?,?);")) {
            for (int i = 1; i <= 100; i++) {
                Car randomCar = generateRandomCar();
                String startDate = generateRandomDate();
                String endDate = generateRandomDateAfterStartDate(startDate);

                stat.setInt(1, i);
                stat.setInt(2, randomCar.getId());
                stat.setString(3, randomCar.getBrand());
                stat.setString(4, randomCar.getModel());
                stat.setString(5, startDate);
                stat.setString(6, endDate);
                stat.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Car generateRandomCar() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            // Check if the 'cars' database is already attached
            ResultSet rs = stmt.executeQuery("PRAGMA database_list;");
            while (rs.next()) {
                String dbName = rs.getString("name");
                if ("cars".equals(dbName)) {
                    // 'cars' database is already attached
                    return getRandomCarFromAttachedDatabase();
                }
            }

            // If 'cars' database is not attached, attach it
            String attachQuery = "ATTACH DATABASE 'C:\\Users\\Marian\\IntelliJProjects\\demo1\\carsDatabase.db' AS cars";
            stmt.executeUpdate(attachQuery);

            // Now get a random car from the attached 'cars' database
            return getRandomCarFromAttachedDatabase();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Car getRandomCarFromAttachedDatabase() throws SQLException {
        try (PreparedStatement stat = connection.prepareStatement("SELECT * FROM cars ORDER BY RANDOM() LIMIT 1")) {
            ResultSet rs = stat.executeQuery();
            if (rs.next()) {
                int carId = rs.getInt(1);
                String brand = rs.getString(2);
                String model = rs.getString(3);
                return new Car(carId, brand, model);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    private static String generateRandomDate() {
        LocalDate startDate = LocalDate.of(2000, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);

        int randomDay = random.nextInt(endDate.getDayOfYear() - startDate.getDayOfYear() + 1) + startDate.getDayOfYear();
        LocalDate randomDate = LocalDate.ofYearDay(endDate.getYear(), randomDay);

        return randomDate.format(dateFormatter);
    }

    private static String generateRandomDateAfterStartDate(String startDateStr) {
        LocalDate startDate = LocalDate.parse(startDateStr, dateFormatter);

        LocalDate endDate = LocalDate.of(2023, 12, 31);

        int randomDay = random.nextInt(endDate.getDayOfYear() - startDate.getDayOfYear() + 1) + startDate.getDayOfYear();
        LocalDate randomDate = LocalDate.ofYearDay(endDate.getYear(), randomDay);

        return randomDate.format(dateFormatter);
    }

    @Override
    public ArrayList<Rent> getAll(){
        ArrayList<Rent> rents = new ArrayList<>();
        try(PreparedStatement stm = connection.prepareStatement("SELECT * FROM rents")){
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                Rent r = new Rent(rs.getInt(1), new Car(rs.getInt(2), rs.getString(3), rs.getString(4)), rs.getString(5), rs.getString(6));
                rents.add(r);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return rents;
    }

    @Override
    public void add(Rent r){
        try(PreparedStatement stm = connection.prepareStatement("INSERT INTO rents values (?,?,?,?,?,?);")){
            stm.setInt(1, r.getId());
            stm.setInt(2,r.getCar().getId());
            stm.setString(3,r.getCar().getBrand());
            stm.setString(4, r.getCar().getModel());
            stm.setString(5,r.getStartDate());
            stm.setString(6, r.getEndDate());
            stm.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    @Override
    public void delete(Rent rent){
        try(PreparedStatement stm = connection.prepareStatement("DELETE FROM rents WHERE id =? ;")){
            stm.setInt(1, rent.getId());
            stm.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(Rent r){
        try(PreparedStatement stm = connection.prepareStatement("UPDATE rents SET id_car = ?, brand = ? , model = ? , data_inceput = ? , data_sfarsit = ? WHERE id = ?")){
            stm.setInt(6, r.getId());
            stm.setInt(1,r.getCar().getId());
            stm.setString(2,r.getCar().getBrand());
            stm.setString(3, r.getCar().getModel());
            stm.setString(4,r.getStartDate());
            stm.setString(5, r.getEndDate());
            stm.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}



