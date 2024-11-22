package com.klosebros.kata.parrot;

public class EuropeanParrot extends Parrot {

    protected EuropeanParrot(double voltage) {
        super(voltage);
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
