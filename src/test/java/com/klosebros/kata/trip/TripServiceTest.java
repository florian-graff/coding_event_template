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
        TripService tripService = new TripServiceTestable(null);
        var user = new User();

        // When
        assertThrows(UserNotLoggedInException.class, () -> tripService.getTripsByUser(user));
    }

    @Test
    void shouldReturnEmptyListIfUserHasNoFriends() {
        // Given
        var user = new User();
        TripService tripService = new TripServiceTestable(user);

        assertThat(tripService.getTripsByUser(user)).isEmpty();
    }

    @Test
    void shouldReturnEmptyListIfUserHasANonLoggedFriend() {
        // Given
        var loggedUser = new User();
        TripService tripService = new TripServiceTestable(loggedUser);

        User userToAsk = new User();
        userToAsk.addFriend(new User());

        assertThat(tripService.getTripsByUser(userToAsk)).isEmpty();
    }

    @Test
    void shouldReturnEmptyListIfUserIsLoggedFriend() {
        // Given
        var loggedUser = new User();
        TripService tripService = new TripServiceTestable(loggedUser);

        User userToAsk = new User();
        userToAsk.addFriend(loggedUser);

        assertThat(tripService.getTripsByUser(userToAsk)).isNotEmpty();
    }
}

class TripServiceTestable extends TripService {

    private final User loggedUser;

    TripServiceTestable(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    @Override
    User getLoggedUser() {
        return loggedUser;
    }

    @Override
    List<Trip> findTripsByUser(User user) {
        return super.findTripsByUser(user);
    }
}
