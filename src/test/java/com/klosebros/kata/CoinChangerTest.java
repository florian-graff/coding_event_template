package com.klosebros.kata;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CoinChangerTest {

    @Test
    void change_0() {
        var coins = new CoinChanger().getCoins(0);
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
    void change_465() {
        var coinChanger = new CoinChanger();
        var coins  = coinChanger.getCoins(465);
        assertThat(coins).containsExactly(200, 200, 50, 10 ,2, 1);
    }



}
