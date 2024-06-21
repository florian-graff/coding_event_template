package com.klosebros.kata;

public class RomanNumerals {
    //new array  = [1000,];

    String convert(int number) {
        if (number < 5) {
           return "I".repeat(number);
        }
        final int multiplicator = number / 1000;
        if (multiplicator > 0) {
            return "M".repeat(multiplicator);
        }
        return singleDigitToNumeral(number);
    }

    private String singleDigitToNumeral(final int number) {
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
