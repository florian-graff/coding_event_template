package com.klosebros.kata.parrot;

public class EuropeanParrot extends Parrot {

    public EuropeanParrot(ParrotTypeEnum type, double voltage, boolean isNailed) {
        super(type, voltage);
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
