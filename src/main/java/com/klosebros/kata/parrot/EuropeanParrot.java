package com.klosebros.kata.parrot;

public class EuropeanParrot extends Parrot {

    protected EuropeanParrot(int numberOfCoconuts, double voltage) {
        super(numberOfCoconuts, voltage);
    }

    @Override
    public double getSpeed() {
        return getBaseSpeed();
    }

    @Override
    public String getCry() {
        return "Sqoork!";
    }
}
