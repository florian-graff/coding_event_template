package com.klosebros.kata;

public class RomanNumerals {
    String convert(int number) {
        if (number < 5) {
           return "I".repeat(number);
        }
        final int multiplicator = number / 1000;

        if (multiplicator > 0) {
            return "M".repeat(multiplicator);
        }

        final int multiplicator100 = number / 100;
        if (multiplicator100 > 0 && multiplicator100 <= 4) {
            return "C".repeat(multiplicator100);
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
            default -> throw new RuntimeException("Fehler ;-)");
        };
    }

}
