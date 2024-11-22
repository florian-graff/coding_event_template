package com.klosebros.kata.parrot;

import static com.klosebros.kata.parrot.ParrotTypeEnum.*;

public abstract class Parrot {

    private final ParrotTypeEnum type;
    protected final int numberOfCoconuts;
    protected final double voltage;
    protected final boolean isNailed;

    protected Parrot(ParrotTypeEnum type, int numberOfCoconuts, double voltage, boolean isNailed) {
        this.type = type;
        this.numberOfCoconuts = numberOfCoconuts;
        this.voltage = voltage;
        this.isNailed = isNailed;
    }

    public static Parrot createParrot(ParrotTypeEnum type, int numberOfCoconuts, double voltage, boolean isNailed) {

        if (type == EUROPEAN) return new EuropeanParrot(type, numberOfCoconuts, voltage, isNailed);
        if (type == AFRICAN) return new AfricanParrot(type, numberOfCoconuts, voltage, isNailed);
        if (type == NORWEGIAN_BLUE) return new NorwegianBlueParrot(type, numberOfCoconuts, voltage, isNailed);
        throw new IllegalStateException("Unexpected parrot type");
    }

    public abstract double getSpeed();

    protected double getBaseSpeed(double voltage) {
        return Math.min(24.0, voltage * getBaseSpeed());
    }

    protected double getLoadFactor() {
        return 9.0;
    }

    protected double getBaseSpeed() {
        return 12.0;
    }

    public String getCry() {
        return switch (type) {
            case EUROPEAN -> "Sqoork!";
            case AFRICAN -> "Sqaark!";
            case NORWEGIAN_BLUE -> voltage > 0 ? "Bzzzzzz" : "...";
        };
    }
}
