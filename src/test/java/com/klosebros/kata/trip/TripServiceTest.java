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
    User user;

    User loggedUser = new User();

    @BeforeEach
    void setUp() {
        tripService = new TripServiceTestable(loggedUser);
        user = new User();
    }

    @Test
    void userWithNoFriendsReturnsEmptyTripList() {
        List<Trip> tripsByUser = tripService.getTripsByUser(user);

        assertThat(tripsByUser).isEmpty();
    }

    @Test
    void userWithFriendsReturnsTripList() {
        user.addFriend(loggedUser);
        user.addTrip(new Trip());

        List<Trip> tripsByUser = tripService.getTripsByUser(user);

        assertThat(tripsByUser).isNotEmpty();

    }

    @Test
    void exceptionThrownIfNoUserLoggedIn() {
        ((TripServiceTestable) tripService).setUser(null);

        assertThatThrownBy(() -> tripService.getTripsByUser(user)).isInstanceOf(UserNotLoggedInException.class);
    }


}