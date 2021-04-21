package com.library.utilities;

import java.util.Scanner;

public class Extras {
    public static int handlingNumberFormatException(String prompt, Scanner sc1) {
        int intInput = 0;
        while (true) {
            System.out.println(prompt);
            String stringInput = sc1.nextLine();
            try {
                intInput = Integer.parseInt(stringInput);
                break;
            } catch (NumberFormatException exe) {
                System.out.println("You must enter a number");
            }
        }
        return intInput;
    }
}
