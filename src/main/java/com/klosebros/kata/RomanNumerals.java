package com.klosebros.kata;

public class RomanNumerals {
    //new array  = [1000,];

    String convert(int number) {
        if (number < 5) {
           return "I".repeat(number);
        }
        return switch (number) {
            case 5 -> "V";
            case 10 -> "X";
            case 50 -> "L";
            case 100 -> "C";
            case 500 -> "D";
            case 1000 -> "M";
            default -> throw new RuntimeException();
        };
    }

}
