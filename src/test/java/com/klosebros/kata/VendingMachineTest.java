package com.klosebros.kata;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VendingMachineTest {

    @Test
    void deleteThisTest() {
        assertThat(false).isTrue();
    }

    @Test
    void vendingMachine_readyToInsertCoin() {
        var vendingMachine = new VendingMachine();
        vendingMachine.getDisplay();

        assertThat(vendingMachine.getDisplay()).isEqualTo("GELD EINWERFEN");
    }
}
