package org.example.fi;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Calculator {
    public static final int EXIT_CODE = 5;

    public static void main(String[] args) {
        try(Scanner sc = new Scanner(System.in)) {
            int option=0;
            do {
                try {
                    menu();
                    option = sc.nextInt();

                    if (option < 1 || option > EXIT_CODE) throw new InputMismatchException();

                    if (option != EXIT_CODE) {

                    }

                } catch (InputMismatchException iem) {
                    System.out.println("Invalid option! Try again.");
                    sc.next();
                }
            } while (option != EXIT_CODE);
        }
    }

    private static int readInt(String s, Scanner sc) {
        System.out.println(s);
        return sc.nextInt();
    }

    private static void menu() {
        System.out.println("Options menu:");
        System.out.println("1 - Sum");
        System.out.println("2 - Subtract");
        System.out.println("3 - Multiply");
        System.out.println("4 - Divide");
        System.out.println("5 - Exit");
    }
}
