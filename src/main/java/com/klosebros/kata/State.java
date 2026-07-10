package com.klosebros.kata;

public interface State {
    String getDisplay();

    void selectProduct(Product product);
}
