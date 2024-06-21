package com.klosebros.kata;

public class RomanNumerals {

    String convert(int i) {
        String romanNumeral;
        switch (i) {
            case 1:
                romanNumeral = "I";
                break;
            case 5:
                romanNumeral = "V";
                break;
            case 10:
                romanNumeral = "X";
                break;
            case 50:
                romanNumeral = "L";
                break;
            case 100:
                romanNumeral = "C";
                break;
            case 500:
                romanNumeral = "D";
                break;
            case 1000:
                romanNumeral = "M";
                break;
            default:
                throw new RuntimeException();
        }
        return romanNumeral;
    }

}
