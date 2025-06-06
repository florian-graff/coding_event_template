package com.klosebros.kata.fillingstation;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class FillingStationShould {

    private static final int FULL = 100;
    private final FillingStation fillingStation = new FillingStation();

    @Test
    public void refuel_a_petrol_car() {
        PetrolCar car = new PetrolCar();

        fillingStation.refuel(car);

        assertThat(car.fuelTankLevel())
                .isEqualTo(FULL);
    }

    @Test
    public void recharge_an_electric_car() {
        ElectricCar car = new ElectricCar();

        fillingStation.charge(car);

        assertThat(car.batteryLevel())
                .isEqualTo(FULL);
    }

}
