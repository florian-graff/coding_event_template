package com.klosebros.kata.parrot;

import static com.klosebros.kata.parrot.ParrotTypeEnum.*;

public abstract class Parrot {

    public static final double LOAD_FACTOR = 9.0;
    public static final double BASE_SPEED = 12.0;
    public static final double MINIMUM_BASE_SPEED = 24.0;
    protected final int numberOfCoconuts;
    protected final double voltage;
    protected final boolean isNailed;

    protected Parrot(int numberOfCoconuts, double voltage, boolean isNailed) {
        this.numberOfCoconuts = numberOfCoconuts;
        this.voltage = voltage;
        this.isNailed = isNailed;
    }

    public static Parrot createParrot(ParrotTypeEnum type, int numberOfCoconuts, double voltage, boolean isNailed) {

        if (type == EUROPEAN) return new EuropeanParrot(numberOfCoconuts, voltage, isNailed);
        if (type == AFRICAN) return new AfricanParrot(numberOfCoconuts, voltage, isNailed);
        if (type == NORWEGIAN_BLUE) return new NorwegianBlueParrot(numberOfCoconuts, voltage, isNailed);
        throw new IllegalStateException("Unexpected parrot type");
    }

    public abstract double getSpeed();

    protected double getBaseSpeed(double voltage) {
        return Math.min(MINIMUM_BASE_SPEED, voltage * getBaseSpeed());
    }

    protected double getLoadFactor() {
        return LOAD_FACTOR;
    }

    protected double getBaseSpeed() {
        return BASE_SPEED;
    }

    public abstract String getCry();
}
