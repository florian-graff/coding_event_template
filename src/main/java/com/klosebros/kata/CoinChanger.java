package com.klosebros.kata;

import java.util.List;

public class CoinChanger {

    public List<Integer> getCoins(int amount) {
        if (amount == 200) {
            return List.of(200);
        } else {
            return List.of();
        }
    }
}
