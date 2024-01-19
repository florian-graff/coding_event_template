package com.klosebros.kata;

import java.util.ArrayList;
import java.util.List;

public class CoinChanger {


    public List<Integer> changeCoins(int value) {
       return changeCoins(value,List.of(200, 100, 50, 20, 10, 5, 2, 1) );
    }

    public List<Integer> changeCoins(int value, List<Integer> validCoins){
        var result = new ArrayList<Integer>();
        int rest = value;
        System.out.printf("Input %s Coins %s%n", rest,result);
        while (rest > 0) {
            for (Integer coin : validCoins) {
                if (coin <= rest) {
                    result.add(coin);
                    rest -= coin;
                    break;
                }
            }
            System.out.printf("Rest %s Coins %s%n", rest,result);
        }
        return result;
    }

}
