package com.klosebros.kata;

import java.util.List;

public class CoinsInsertedState implements State {


    private static final String BAD_COIN = "Münze ungültig";
    private final List<Coin> coins;

    public CoinsInsertedState(List<Coin> coins) {
        this.coins = coins;
    }

    @Override
    public String getDisplay() {
        if (coins.contains(Coin.ZEHN_CENT)) {
            return "0,10 EURO EINGEWORFEN";
        } else if (coins.contains(Coin.ZWANZIG_CENT)) {
            return "0,20 EURO EINGEWORFEN";
        } else {
            return BAD_COIN;
        }
    }
}
