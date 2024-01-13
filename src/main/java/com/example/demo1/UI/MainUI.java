package com.example.demo1.UI;

import java.io.IOException;
import java.util.Scanner;
public class MainUI {
    private CarUI carUI;
    private RentUI rentUI;
    private Scanner scanner;
    public MainUI(CarUI carUI, RentUI rentUI) {
        this.carUI = carUI;
        this.rentUI = rentUI;
        this.scanner = new Scanner(System.in);
    }

    public void runMenu() throws IOException {
        while (true) {
            System.out.println("Main Menu:");
            System.out.println("1. Car Menu");
            System.out.println("2. Rent Menu");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    carUI.runMenu();
                    break;
                case 2:
                    rentUI.runMenu();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid option... try again!");
            }
        }
    }

}
