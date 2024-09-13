package com.klosebros.kata.trip;

import com.klosebros.kata.user.User;

import java.util.List;

public class TripServiceTestable extends TripService {

    private final User user;

    public TripServiceTestable(User user) {
        this.user = user;
    }

    @Override
    protected User getLoggedUser() {
        return user;
    }

    @Override
    protected List<Trip> getByUser(User user) {
        return user.trips();
    }

}
