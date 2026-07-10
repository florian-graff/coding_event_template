package com.klosebros.kata;

import java.util.ArrayList;
import java.util.List;

public class VendingMachine {

    private static final String INSERT_COIN = "GELD EINWERFEN";
    private String display = INSERT_COIN;
    private List<Coin> coins = new ArrayList<>();

    public String getDisplay() {
        return display;
    }

    public void insertCoin(Coin coin) {
        coins.add(coin);
        display = "0,10 EURO EINGEWORFEN";
    }

    public List<Coin> returnCoins() {
        display = INSERT_COIN;
        return coins;
    }
}
