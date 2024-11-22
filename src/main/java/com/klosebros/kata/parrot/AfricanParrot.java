package com.klosebros.kata.parrot;

public class AfricanParrot extends Parrot {
    final int numberOfCoconuts;

    protected AfricanParrot(ParrotTypeEnum type, int numberOfCoconuts, double voltage, boolean isNailed) {
        super(type, voltage);
        this.numberOfCoconuts = numberOfCoconuts;
    }

    @Override
    public String getCry() {
        return "Sqaark!";
    }

    @Override
    public double getSpeed() {
        return Math.max(0, getBaseSpeed() - getLoadFactor() * numberOfCoconuts);
    }

    double getLoadFactor() {
        return 9.0;
    }
}
