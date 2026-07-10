package com.klosebros.kata;

import java.util.ArrayList;
import java.util.List;

public class VendingMachine {

    private final List<Coin> coins = new ArrayList<>();
    private State state = new NoCoinsState();

    public String getDisplay() {
        return state.getDisplay();
    }

    public void insertCoin(Coin coin) {
        coins.add(coin);
        state = new CoinsInsertedState(coins);
    }

    public List<Coin> returnCoins() {
        List<Coin> returnedCoins = new ArrayList<>(coins);
        coins.clear();
        state = new NoCoinsState();
        return returnedCoins;
    }

    public void selectProduct(Product product) {
        state.selectProduct(product);
        state = new ProductBuySuccessfulState();
    }
}
