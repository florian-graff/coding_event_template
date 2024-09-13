package com.klosebros.kata.trip;

import com.klosebros.kata.exception.UserNotLoggedInException;
import com.klosebros.kata.user.User;
import com.klosebros.kata.user.UserSession;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class TripServiceTest {

    @Test
    void shouldThrowExceptionIfLoggedUserIsNull() {
        // Given
        TripService tripService = new TripService();
        User user = new User();

        // When
        assertThrows(UserNotLoggedInException.class, () -> tripService.getTripsByUser(user));
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
