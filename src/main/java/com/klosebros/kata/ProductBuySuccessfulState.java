package com.klosebros.kata;

public class ProductBuySuccessfulState implements State {
    @Override
    public String getDisplay() {
        return "VIELEN DANK";
    }

    @Override
    public void selectProduct(Product product) {

    }
}
