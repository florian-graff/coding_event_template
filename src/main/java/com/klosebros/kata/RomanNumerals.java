package com.klosebros.kata;

import java.util.List;


public class RomanNumerals {

    private final List<NumberNumeralPair> numberNumeralPairs = List.of(
            new NumberNumeralPair(1000, "M"),
            new NumberNumeralPair(100, "C"));

    String convert(int number) {
        if (number < 5) {
           return "I".repeat(number);
        }

        for (var numberNumeralPair : numberNumeralPairs) {
            final int multiplicator = number / numberNumeralPair.number();

            if (multiplicator > 0) {
                return numberNumeralPair.numeral().repeat(multiplicator);
            }
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
