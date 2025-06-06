package com.klosebros.kata.fillingstation;

public class ElectricCar extends Vehicle implements NeedsElectricity {

    private static final int BATTERY_FULL = 100;
    private int batteryLevel;

    @Override
    public void chargeBattery() {
        batteryLevel = BATTERY_FULL;
    }

    public int batteryLevel() {
        return batteryLevel;
    }
}
