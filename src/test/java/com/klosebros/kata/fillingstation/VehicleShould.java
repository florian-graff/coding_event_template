package com.klosebros.kata.fillingstation;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class VehicleShould {
  @Test
  public void start_engine() {
    Vehicle vehicle = new TestableVehicle();

    vehicle.startEngine();

    assertThat(vehicle.engineIsStarted())
      .isTrue();

  }

  @Test
  public void stop_engine() {
    Vehicle vehicle = new TestableVehicle();

    vehicle.startEngine();
    vehicle.stopEngine();

    assertThat(vehicle.engineIsStarted())
      .isFalse();
  }


  public class TestableVehicle extends Vehicle implements NeedsFuel, NeedsElectricity {

    @Override
    public void fillUpWithFuel() {

    }

    @Override
    public int fuelTankLevel() {
      return 0;
    }

    @Override
    public void chargeBattery() {

    }
  }

}