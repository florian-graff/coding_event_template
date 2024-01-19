package com.klosebros.kata;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CoinChangerTest {

    @Test
    void testchange200(){
        CoinChanger coinChanger = new CoinChanger();
        var expected = List.of(200);
        assertThat(coinChanger.changeCoins(200)).isEqualTo(expected);
    }

    @Test
    void testchange250(){
        CoinChanger coinChanger = new CoinChanger();
        var expected = List.of(200,50);
        assertThat(coinChanger.changeCoins(250)).isEqualTo(expected);
    }

    @Test
    void testchange261(){
        CoinChanger coinChanger = new CoinChanger();
        var expected = List.of(200,50,10,1);
        assertThat(coinChanger.changeCoins(261)).isEqualTo(expected);
    }

    @Test
    void testchange461(){
        CoinChanger coinChanger = new CoinChanger();
        var expected = List.of(200,200,50,10,1);
        assertThat(coinChanger.changeCoins(461)).isEqualTo(expected);
    }
}