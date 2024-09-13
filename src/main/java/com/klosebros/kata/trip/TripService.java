package com.klosebros.kata.trip;


import com.klosebros.kata.exception.UserNotLoggedInException;
import com.klosebros.kata.user.User;
import com.klosebros.kata.user.UserSession;

import java.util.ArrayList;
import java.util.List;

public class TripService {

    public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
        List<Trip> tripList = new ArrayList<Trip>();
        var loggedUser = getLoggedUser();
        if (loggedUser == null) {
            throw new UserNotLoggedInException();
        }
        var isLoggedUserFriend = user.getFriends().stream()
                .anyMatch(friend -> friend.equals(loggedUser));

        if (isLoggedUserFriend) {
            tripList = findTripsByUser(user);
        }
        return tripList;
    }

    List<Trip> findTripsByUser(User user) {
        return TripDAO.findTripsByUser(user);
    }

    User getLoggedUser() {
        return UserSession.getInstance().getLoggedUser();
    }

}
