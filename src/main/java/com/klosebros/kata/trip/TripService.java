package com.klosebros.kata.trip;


import com.klosebros.kata.exception.UserNotLoggedInException;
import com.klosebros.kata.user.User;
import com.klosebros.kata.user.UserSession;

import java.util.ArrayList;
import java.util.List;

public class TripService {

    private final TripDAO tripDAO;
    private final UserSession userSession;

    public TripService(TripDAO tripDAO, UserSession userSession) {
        this.tripDAO = tripDAO;
        this.userSession = userSession;
    }

    public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
        List<Trip> tripList = new ArrayList<>();
        var loggedUser = getLoggedUser();
        if (loggedUser == null) {
            throw new UserNotLoggedInException();
        }
        var isLoggedUserFriend = user.getFriends().stream()
                .anyMatch(friend -> friend.equals(loggedUser));

        if (isLoggedUserFriend) {
            tripList = tripDAO.findTripsByUser(user);
        }
        return tripList;
    }

    User getLoggedUser() {
        return UserSession.getInstance().getLoggedUser();
    }

}
