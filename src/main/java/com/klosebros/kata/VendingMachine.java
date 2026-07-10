package com.klosebros.kata;

public class VendingMachine {

    private static final String INSERT_COIN = "GELD EINWERFEN";
    private String display = INSERT_COIN;

    public String getDisplay() {
        return display;
    }

    public void insertCoin(Coin coin) {
        display = "0,10 EURO EINGEWORFEN";
    }
}
