package com.klosebros.kata;

import java.util.ArrayList;
import java.util.List;

public class CoinChanger {

    public List<Integer> getCoins(int amount) {
        var coins = new ArrayList<Integer>();
        if (amount >= 200) {
             coins.addAll(amount/200 * 200 , getCoins(amount % 200));
            return coins;
        } else {
            return List.of();
        }
    }
}
