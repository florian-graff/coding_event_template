//package com.klosebros.kata;

import java.util.ArrayList;
import java.util.List;

public class CoinChanger {

    final  List<Integer> denominations ;

    public CoinChanger(List<Integer> denominations) {
        this.denominations =  denominations;
    }

    public List<Integer> getCoins(int amount) {
        var coins = new ArrayList<Integer>();
        for (Integer denomination : denominations) {
            if (amount >= denomination) {
                calcCoins(amount, denomination, coins);
                break;
            }
        }
        return coins;
    }

    private void calcCoins(int amount, int x, ArrayList<Integer> coins) {
        for (int i = 0; i < amount / x; i++) {
            coins.add(x);
        }
        var rest = getCoins(amount % x);
        if (!rest.isEmpty()) {
            coins.addAll(rest);
        }
    }
}
