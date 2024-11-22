package com.klosebros.kata.parrot;

public class NorwegianBlueParrot extends Parrot {
    protected final boolean isNailed;

    protected NorwegianBlueParrot(double voltage, boolean isNailed) {
        super(voltage);
        this.isNailed = isNailed;
    }

    @Override
    public double getSpeed() {
        return (isNailed) ? 0 : getBaseSpeed(voltage);
    }

    @Override
    public String getCry() {
        return voltage > 0 ? "Bzzzzzz" : "...";
    }
}
