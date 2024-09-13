package com.klosebros.kata.trip;

import com.klosebros.kata.exception.UserNotLoggedInException;
import com.klosebros.kata.user.User;
import org.junit.jupiter.api.Test;

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

        tripService.getTripsByUser(user);

        assertThat(tripService.getTripsByUser(user)).isEmpty();
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
}
