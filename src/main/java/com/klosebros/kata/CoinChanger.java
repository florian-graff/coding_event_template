package com.klosebros.kata;

import java.util.ArrayList;
import java.util.List;

public class CoinChanger {


    public List<Integer> getCoins(int amount) {
        var coins = new ArrayList<Integer>();
        if (amount >= 200) {
            calcCoins(amount, 200, coins);
        } else if (amount >= 100) {
            calcCoins(amount, 100, coins);
        } else if (amount >= 50) {
            calcCoins(amount, 50, coins);
        } else if (amount >= 20) {
            calcCoins(amount, 20, coins);
        } else if (amount >= 10) {
            calcCoins(amount, 10, coins);
        } else if (amount >= 5) {
            calcCoins(amount, 5, coins);
        } else if (amount >= 2) {
            calcCoins(amount, 2, coins);
        } else if (amount >= 1) {
            calcCoins(amount, 1, coins);
        }
        return coins;
    }

    private void calcCoins(int amount, int x, ArrayList<Integer> coins) {
        for (int i=0 ;i<amount / x;i++) {
            coins.add(x);
        }
        var rest = getCoins(amount % x);
        if (!rest.isEmpty()) {
            coins.addAll(rest);
        }
    }
}
