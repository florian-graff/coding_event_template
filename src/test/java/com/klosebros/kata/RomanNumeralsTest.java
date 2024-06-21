package com.klosebros.kata;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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
    @Test
    void testAllSymbols() {
        var romanNumerals = new RomanNumerals();
        var result = romanNumerals.convert(1);
        assertThat(result).isEqualTo("I");
    }

    
}