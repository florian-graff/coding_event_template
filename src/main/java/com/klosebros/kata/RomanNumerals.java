package com.klosebros.kata;

public class RomanNumerals {

    String convert(int number) {
        return switch (number) {
            case 1 -> "I";
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
