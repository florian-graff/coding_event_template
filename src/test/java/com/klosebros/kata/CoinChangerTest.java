package com.klosebros.kata;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CoinChangerTest {

    @Test
    void testChangeCoins() {
        int input = 463;
        int[] expectedOutput = new int[]{200,200,50,10,2,1};

        int[] resultOutput = CoinChanger.changeCoins(input);

        assertEquals(resultOutput, expectedOutput);
    }

    @Test
    void deleteThisTest() {
        assertThat(false).isTrue();
    }
}