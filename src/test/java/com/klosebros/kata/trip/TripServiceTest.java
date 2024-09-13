package com.klosebros.kata.trip;

import com.klosebros.kata.exception.UserNotLoggedInException;
import com.klosebros.kata.user.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TripServiceTest {

    @Test
    void userWithNoFriendsReturnsEmptyTripList() {
        TripService tripService = new TripServiceTestable(new User());
        User user = new User();

        List<Trip> tripsByUser = tripService.getTripsByUser(user);

        assertThat(tripsByUser).isEmpty();

    }

    @Test
    void userWithFriendsReturnsTripList() {
        User user = new User();
        user.addFriend(user);
        user.addTrip(new Trip());

        TripService tripService = new TripServiceTestable(user);

        List<Trip> tripsByUser = tripService.getTripsByUser(user);

        assertThat(tripsByUser).isNotEmpty();

    }

    @Test
    void exceptionThrownIfNoUserLoggedIn() {
        TripService tripService = new TripServiceTestable(null);
        User user = new User();

        try {
            tripService.getTripsByUser(user);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(UserNotLoggedInException.class);
        }
    }
}