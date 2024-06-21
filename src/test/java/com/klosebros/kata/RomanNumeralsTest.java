package com.klosebros.kata;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class RomanNumeralsTest {

    @Test
    void testOne() {
        var romanNumerals = new RomanNumerals();
        var result = romanNumerals.convert(1);
        assertThat(result).isEqualTo("I");
    }

    @ParameterizedTest
    @CsvSource({"1,I","5,V","10,X","50,L","100,C","500,D","1000,M"})
    void testAllSymbols(int number, String numeral) {
        var romanNumerals = new RomanNumerals();
        var result = romanNumerals.convert(number);
        assertThat(result).isEqualTo(numeral);
    }

    @TestFactory
    Stream<DynamicTest> translateDynamicTestsFromStream() {
        var numbers = Map.of(1, "I", 2, "II", 3, "III", 4, "IIII");

        return numbers.entrySet().stream().map(e -> DynamicTest.dynamicTest("Test for " + e.getKey(), () -> {
            var number = e.getKey();
            var numerals = e.getValue();

            var romanNumerals = new RomanNumerals();
            var result = romanNumerals.convert(number);
            assertThat(result).isEqualTo(numerals);
        }));
    }
    
}
