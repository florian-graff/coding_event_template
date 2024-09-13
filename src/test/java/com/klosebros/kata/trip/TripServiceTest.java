package com.klosebros.kata.trip;

import com.klosebros.kata.user.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TripServiceTest {

    @Test
    void getTripsByUserNoFriends() {
        TripService tripService = new TripServiceTestable(new User());
        User user = new User();

        List<Trip> tripsByUser = tripService.getTripsByUser(user);

        assertThat(tripsByUser).isEmpty();

    }

    @Test
    void getTripsByUserIsFriend() {
        User user = new User();
        user.addFriend(user);
        TripService tripService = new TripServiceTestable(user);

        List<Trip> tripsByUser = tripService.getTripsByUser(user);

        assertThat(tripsByUser).isNotEmpty();

    }
}