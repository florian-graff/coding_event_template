package com.klosebros.kata.parrot;

public abstract class Parrot  {

    final ParrotTypeEnum type;
    final double voltage;

    public static Parrot create(ParrotTypeEnum type, int numberOfCoconuts, double voltage, boolean isNailed) {
        return switch (type) {
            case EUROPEAN -> new EuropeanParrot(type, voltage, isNailed);
            case AFRICAN -> new AfricanParrot(type, numberOfCoconuts, voltage, isNailed);
            case NORWEGIAN_BLUE -> new NorwegianBlueParrot(type, voltage, isNailed);
        };
    }

    protected Parrot(ParrotTypeEnum type, double voltage) {
        this.type = type;
        this.voltage = voltage;
    }

    public abstract double getSpeed();

    public double getBaseSpeed(double voltage) {
        return Math.min(24.0, voltage * getBaseSpeed());
    }

    double getBaseSpeed() {
        return 12.0;
    }

    public abstract String getCry();
}
