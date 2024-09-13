package com.klosebros.kata.trip;

import com.klosebros.kata.exception.UserNotLoggedInException;
import com.klosebros.kata.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TripServiceTest {

    TripService tripService;

    @BeforeEach
    void setUp() {
        tripService = new TripServiceTestable(new User());
    }

    @Test
    void userWithNoFriendsReturnsEmptyTripList() {
        User user = new User();

        List<Trip> tripsByUser = tripService.getTripsByUser(user);

        assertThat(tripsByUser).isEmpty();
    }

    @Test
    void userWithFriendsReturnsTripList() {
        User user = new User();
        user.addFriend(user);
        user.addTrip(new Trip());

        List<Trip> tripsByUser = tripService.getTripsByUser(user);

        assertThat(tripsByUser).isNotEmpty();

    }

    @Test
    void exceptionThrownIfNoUserLoggedIn() {
        User user = new User();

        assertThatThrownBy(() -> tripService.getTripsByUser(user)).isInstanceOf(UserNotLoggedInException.class);
    }


}