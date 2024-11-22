package com.klosebros.kata.parrot;

public class AfricanParrot extends Parrot {

    private final int numberOfCoconuts;

    protected AfricanParrot(int numberOfCoconuts, double voltage) {
        super(voltage);
        this.numberOfCoconuts = numberOfCoconuts;
    }

    @Override
    public double getSpeed() {
        return Math.max(0, getBaseSpeed() - getLoadFactor() * numberOfCoconuts);
    }

    @Override
    public String getCry() {
        return "Sqaark!";
    }
}
