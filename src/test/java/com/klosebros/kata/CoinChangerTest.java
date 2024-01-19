package com.klosebros.kata;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CoinChangerTest {

    private CoinChanger coinChanger = new CoinChanger(List.of(200, 100, 50, 20, 10, 5, 2, 1));

    @Test
    void change_0() {
        var coins = coinChanger.getCoins(0);
        assertThat(coins).isEmpty();
    }

    @Test
    void change_200() {
        var coinChanger = new CoinChanger();
        var coins  = coinChanger.getCoins(200);
        assertThat(coins).containsExactly(200);
    }

    @Test
    void change_300() {
        var coinChanger = new CoinChanger();
        var coins  = coinChanger.getCoins(300);
        assertThat(coins).containsExactly(200, 100);
    }
    @Test
    void change_463() {
        var coinChanger = new CoinChanger();
        var coins  = coinChanger.getCoins(463);
        assertThat(coins).containsExactly(200, 200, 50, 10 ,2, 1);
    }



}
