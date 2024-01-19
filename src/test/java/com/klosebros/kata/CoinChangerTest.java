package com.klosebros.kata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

class CoinChangerTest {

    private final CoinBank coinBankMock = Mockito.mock(CoinBank.class);
    private final CoinChanger coinChanger = new CoinChanger(List.of(200, 100, 50, 20, 10, 5, 2, 1), coinBankMock);

    @BeforeEach
    void setUp() {
        when(coinBankMock.hasMoney(anyInt())).thenReturn(true);
    }

    @Test
    void change_0() {
        var coins = coinChanger.getCoins(0);
        assertThat(coins).isEmpty();
    }

    @Test
    void change_200() {
        var coins = coinChanger.getCoins(200);
        assertThat(coins).containsExactly(200);
    }

    @Test
    void change_300() {
        var coins = coinChanger.getCoins(300);
        assertThat(coins).containsExactly(200, 100);
    }

    @Test
    void change_463() {
        var coins = coinChanger.getCoins(463);
        assertThat(coins).containsExactly(200, 200, 50, 10, 2, 1);
    }

    @Test
    void change_563() {
        var coinChanger = new CoinChanger(List.of(500, 200, 100, 50, 20, 10, 5, 2, 1), coinBankMock);
        var coins = coinChanger.getCoins(563);
        assertThat(coins).containsExactly(500, 50, 10, 2, 1);
    }

    @Test
    void bank_is_empty() {
        when(coinBankMock.hasMoney(563)).thenReturn(false);
        var coinChanger = new CoinChanger(List.of(500, 200, 100, 50, 20, 10, 5, 2, 1), coinBankMock);
        var coins = coinChanger.getCoins(563);
        assertThat(coins).isEmpty();
    }
}
