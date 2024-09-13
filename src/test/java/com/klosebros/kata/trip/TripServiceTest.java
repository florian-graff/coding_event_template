package com.klosebros.kata.trip;

import com.klosebros.kata.user.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TripServiceTest {

    @Test
    void getTripsByUser() {
        TripService tripService = new TripService();
        User user = new User();

        List<Trip> tripsByUser = tripService.getTripsByUser(user);

        assertThat(tripsByUser).isNotEmpty();

    }
}