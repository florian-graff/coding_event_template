package com.klosebros.kata;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CoinChangerTest {

    @Test
    void change_0() {
        var coins = new CoinChanger().getCoins(0);
        assertThat(coins).isEmpty();
    }
}
