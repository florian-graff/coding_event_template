package com.klosebros.kata;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VendingMachineTest {

    @Test
    void vendingMachine_readyToInsertCoin() {
        var vendingMachine = new VendingMachine();

        assertThat(vendingMachine.getDisplay()).isEqualTo("GELD EINWERFEN");
    }

    @Test
    void vendingMachine_insertCoinZehnCent() {
        var vendingMachine = new VendingMachine();
        vendingMachine.insertCoin(Coin.ZEHN_CENT);

        assertThat(vendingMachine.getDisplay()).isEqualTo("0,10 EURO EINGEWORFEN");
    }

    @Test
    void vendingMachine_insertCoinZwanzigCent() {
        var vendingMachine = new VendingMachine();
        vendingMachine.insertCoin(Coin.ZWANZIG_CENT);

        assertThat(vendingMachine.getDisplay()).isEqualTo("0,20 EURO EINGEWORFEN");
    }

    @Test
    void vendingMachine_returnCoin() {
        var vendingMachine = new VendingMachine();
        vendingMachine.insertCoin(Coin.ZEHN_CENT);
        var coins = vendingMachine.returnCoins();

        assertThat(coins).containsExactly(Coin.ZEHN_CENT);
        assertThat(vendingMachine.getDisplay()).isEqualTo("GELD EINWERFEN");
    }
}
