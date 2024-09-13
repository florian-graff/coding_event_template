package com.klosebros.kata.trip;

import com.klosebros.kata.exception.UserNotLoggedInException;
import com.klosebros.kata.user.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TripServiceTest {

    @Test
    void shouldThrowExceptionIfLoggedUserIsNull() {
        // Given
        TripService tripService = new TripServiceTestable(null, List.of());
        var user = new User();

        // When
        assertThrows(UserNotLoggedInException.class, () -> tripService.getTripsByUser(user));
    }

    @Test
    void shouldReturnEmptyListIfUserHasNoFriends() {
        // Given
        var user = new User();
        TripService tripService = new TripServiceTestable(user, List.of());

        assertThat(tripService.getTripsByUser(user)).isEmpty();
    }

    @Test
    void shouldReturnEmptyListIfUserHasANonLoggedFriend() {
        // Given
        var loggedUser = new User();
        TripService tripService = new TripServiceTestable(loggedUser, List.of());

        User userToAsk = new User();
        userToAsk.addFriend(new User());

        assertThat(tripService.getTripsByUser(userToAsk)).isEmpty();
    }

    @Test
    void shouldReturnNotEmptyListIfUserIsLoggedFriend() {
        // Given
        var loggedUser = new User();
        var trip = new Trip();
        TripService tripService = new TripServiceTestable(loggedUser, List.of(trip));

        User userToAsk = new User();
        userToAsk.addFriend(loggedUser);

        assertThat(tripService.getTripsByUser(userToAsk)).containsExactly(trip);
    }
}

class TripServiceTestable extends TripService {

    private final User loggedUser;
    private final List<Trip> trips;

    TripServiceTestable(User loggedUser, List<Trip> trips) {
        this.loggedUser = loggedUser;
        this.trips = trips;
    }

    @Override
    User getLoggedUser() {
        return loggedUser;
    }

    @Override
    List<Trip> findTripsByUser(User user) {
        return trips;
    }
}
