package com.klosebros.kata.fillingstation;

public class FillingStation {

    public void refuel(NeedsFuel vehicle) {
        vehicle.fillUpWithFuel();
    }

    public void charge(NeedsElectricity vehicle) {
        vehicle.chargeBattery();
    }
}
