package com.example.demo1.Domain;

public class RentFactory implements IEntityFactory<Rent>{
    @Override
    public Rent createEntity(String line) {
        int id = Integer.parseInt(line.split(",")[0]);
        int idCar = Integer.parseInt(line.split(",")[1]);
        String brandCar = line.split(",")[2];
        String modelCar = line.split(",")[3];
        String startDate = line.split(",")[4];
        String endDate = line.split(",")[5];

        Car car = new Car(idCar,brandCar, modelCar);
        return new Rent(id, car, startDate, endDate);
    }
    @Override
    public String toStringEntity(Rent rent) {
        return rent.getId() + "," + rent.getCar().toSimpleString2() + "," + rent.getStartDate() + "," + rent.getEndDate();
    }
}
