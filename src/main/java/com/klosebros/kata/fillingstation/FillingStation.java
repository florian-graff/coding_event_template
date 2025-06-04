package com.klosebros.kata.fillingstation;

public class FillingStation {

    public void refuel(Vehicle vehicle) {
        if (vehicle instanceof PetrolCar) {
            vehicle.fillUpWithFuel();
        }
    }

    public void charge(Vehicle vehicle) {
        if (vehicle instanceof ElectricCar) {
            vehicle.chargeBattery();
        }
    }
}
