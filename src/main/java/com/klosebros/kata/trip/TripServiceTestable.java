package com.klosebros.kata.trip;

import com.klosebros.kata.user.User;

public class TripServiceTestable extends TripService {

    @Override
    protected User getLoggedUser() {
        return new User();
    }

}
