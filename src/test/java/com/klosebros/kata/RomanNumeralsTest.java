package com.klosebros.kata;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RomanNumeralsTest {

    @Test
    void testOne() {
        var romanNumerals = new RomanNumerals();
        var result = romanNumerals.convert(1);
        assertThat(result).isEqualTo("I");
    }

}