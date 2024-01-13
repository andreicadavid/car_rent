package com.example.demo1.Service;


import com.example.demo1.Domain.Car;
import com.example.demo1.Domain.Rent;
import com.example.demo1.Exception.RentException;
import com.example.demo1.Repository.RepositoryInterface;
import javafx.util.Pair;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class RentService {

    RepositoryInterface<Rent> rentRepository;

    public RentService(RepositoryInterface<Rent> rentRepository){
        this.rentRepository = rentRepository;
    }

    public void addRent(int id, Car car, String startDate, String endDate) throws RentException, IOException {
        if (id < 0) {
            throw new RentException("Invalid id, it can't be negative");
        }
        for(Rent rent: rentRepository.getAll()) {
            if(rent.getId() == id)
                throw new RentException("It already exists a rent with this ID: " + id);
        }

        Rent rent = new Rent(id, car, startDate, endDate);
        rentRepository.add(rent);
    }

    public List<Rent> getAllRents() {
        return rentRepository.getAll();
    }

    public Rent getRentById(int id) throws RentException {
        Rent rent = rentRepository.getById(id);
        if(rent == null) {
            throw new RentException("Couldn't find a rent with this ID: " + id);
        }

        return rent;
    }

    public void updateRent(int id, String newStartDate, String newEndDate) throws RentException, IOException {
        Rent rent = rentRepository.getById(id);
        if(rent == null) {
            throw new RentException("Couldn't find a rent with this ID: " + id);
        }

        rent.setStartDate(newStartDate);
        rent.setEndDate(newEndDate);
        rentRepository.update(rent);
    }

    public void deleteRent(int id) throws RentException, IOException {
        Rent rent = rentRepository.getById(id);
        if(rent == null) {
            throw new RentException("Couldn't find a rent with this ID: " + id);
        }

        rentRepository.delete(rent);
    }

    public List<Map.Entry<Pair<String, String>, Integer>>  onShowMostRentedCarsButtonClick() {
        // (Marca, Model): nrAparitii
        Map<Pair<String, String>, Integer> nrAparitiiMasina = new HashMap<>();

        List<Rent> listaMasiniInchiriate = rentRepository.getAll();

        for (Rent inchiriere : listaMasiniInchiriate) {
            Car masina = inchiriere.getCar();

            // Luam Marca si Modelul fiecarei masini adaugate deja in lista noastra de inchirieri
            Pair<String, String> masinaInchiriata = new Pair<>(masina.getBrand(), masina.getModel());

            // Verificam daca masina din lista de inchirieri apare de mai multe ori in Map ul nostru
            if (containsKey(nrAparitiiMasina, masinaInchiriata)) {
                // Daca avem aceasta masina adaugata
                Integer value = nrAparitiiMasina.get(masinaInchiriata);
                nrAparitiiMasina.put(masinaInchiriata, value + 1);
            } else {
                // Daca masina pe care o avem la inchiriat, nu este inca adaugata in Map, o adaugam cu valoarea 1
                int value = 0;
                nrAparitiiMasina.put(masinaInchiriata, value + 1);
            }
        }

        // Formam o lista cu masinile inchiriate, sortate descrescator dupa numarul de inchirieri din lista
        List<Map.Entry<Pair<String, String>, Integer>> mostRentedCars = new ArrayList<>(nrAparitiiMasina.entrySet());

        mostRentedCars.sort(Map.Entry.<Pair<String, String>, Integer>comparingByValue().reversed());
        return  mostRentedCars;

    }



    // Verificam daca avem masina
    private boolean containsKey(Map<Pair<String, String>, Integer> map, Pair<String, String> key) {
        for (Pair<String, String> mapKey : map.keySet()) {
            if (mapKey.getKey().equals(key.getKey()) && mapKey.getValue().equals(key.getValue())) {
                return true;
            }
        }
        return false;
    }


    public List<Map.Entry<Pair<String, String>, Integer>> onShowNrOfRentedCarsPerMonthButtonClick() {

        // (Marca, Model): nrAparitii
        Map<Pair<String, String>, Integer> nrInchirieriMasiniPerLuna = new HashMap<>();

        List<Rent> listaMasiniInchiriate = rentRepository.getAll();

        for (Rent inchiriere : listaMasiniInchiriate) {

            // Ne intereseaza decat luna de inceput a inchirierii
            String[] elemente_dataInceput = inchiriere.getStartDate().split("-");
            String luna = elemente_dataInceput[1];
            String an = elemente_dataInceput[2];

            // Luam luna si anul fiecarei masini inchiriate deja
            Pair<String, String> lunaAn = new Pair<>(luna, an);

            // Verificam daca masina din lista de inchirieri apare de mai multe ori in Map ul nostru
            if (containsKey(nrInchirieriMasiniPerLuna, lunaAn)) {
                // Daca avem aceasta masina adaugata
                Integer value = nrInchirieriMasiniPerLuna.get(lunaAn);
                nrInchirieriMasiniPerLuna.put(lunaAn, value + 1);
            } else {
                // Daca masina pe care o avem la inchiriat, nu este inca adaugata in Map, o adaugam cu valoarea 1
                int value = 0;
                nrInchirieriMasiniPerLuna.put(lunaAn, value + 1);
            }
        }

        // Formam o lista cu <luna, an>, sortate descrescator dupa numarul de inchirieri din lista
        List<Map.Entry<Pair<String, String>, Integer>> monthsWithNrOfCars = new ArrayList<>(nrInchirieriMasiniPerLuna.entrySet());

        monthsWithNrOfCars.sort(Map.Entry.<Pair<String, String>, Integer>comparingByValue().reversed());

        return monthsWithNrOfCars;
    }


    public  List<Map.Entry<Car, Long>> ShowLongestRentedCars() {
        List<Rent> listaMasiniInchiriate = rentRepository.getAll();
        Map<Car, Long> masinaPlusNrZileInchiriata = new HashMap<>();

        long nrZileMasinaInchiriata = 0;
        for (Rent inchiriere : listaMasiniInchiriate) {
            Car masina = inchiriere.getCar();
            String dataInceput = inchiriere.getStartDate();
            String dataSfarsit = inchiriere.getEndDate();

            nrZileMasinaInchiriata = returneazaNrDeZile(dataInceput, dataSfarsit);

            masinaPlusNrZileInchiriata.put(masina, nrZileMasinaInchiriata);
        }

        // Formam o lista cu masinile inchiriate, sortate descrescator dupa numarul de inchirieri din lista
        List<Map.Entry<Car, Long>> longestRentedCars = new ArrayList<>(masinaPlusNrZileInchiriata.entrySet());

        // Sortam descrescator lista dupa Values
        longestRentedCars.sort(Map.Entry.<Car, Long>comparingByValue().reversed());

        return longestRentedCars;
    }

    private Long returneazaNrDeZile(String dataInceput, String dataSfarsit) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        // Parsarea string-urilor in obiecte LocalDate
        LocalDate date1 = LocalDate.parse(dataInceput, formatter);
        LocalDate date2 = LocalDate.parse(dataSfarsit, formatter);

        // Calcularea numărului de zile între cele două date
        long nrZile = Math.abs(date1.toEpochDay() - date2.toEpochDay());
        return nrZile;
    }
}
