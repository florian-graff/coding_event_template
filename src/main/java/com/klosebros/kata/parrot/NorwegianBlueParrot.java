package com.klosebros.kata.parrot;

public class NorwegianBlueParrot extends Parrot {

    private final boolean isNailed;

    protected NorwegianBlueParrot(ParrotTypeEnum type, double voltage, boolean isNailed) {
        super(type, voltage);
        this.isNailed = isNailed;
    }

    @Override
    public String getCry() {
        return voltage > 0 ? "Bzzzzzz" : "...";
    }

    @Override
    public double getSpeed() {
        return (isNailed) ? 0 : getBaseSpeed(voltage);
    }

}
