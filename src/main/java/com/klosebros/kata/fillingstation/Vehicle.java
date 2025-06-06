package com.klosebros.kata.fillingstation;

public abstract class Vehicle {

    private boolean engineStarted = false;

    public void startEngine() {
        this.engineStarted = true;
    }

    public boolean engineIsStarted() {
        return engineStarted;
    }

    public void stopEngine() {
        this.engineStarted = false;
    }

}
