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
        var isLoggedUserFriend = false;
		if (loggedUser != null) {
			for (var friend : user.getFriends()) {
				if (friend.equals(loggedUser)) {
					isLoggedUserFriend = true;
					break;
				}
			}
			if (isLoggedUserFriend) {
				tripList = findTripsByUser(user);
			}
			return tripList;
		} else {
			throw new UserNotLoggedInException();
		}
	}

	List<Trip> findTripsByUser(User user) {
		return TripDAO.findTripsByUser(user);
	}

	User getLoggedUser() {
		return UserSession.getInstance().getLoggedUser();
	}

}
