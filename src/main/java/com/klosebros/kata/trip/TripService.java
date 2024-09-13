package com.klosebros.kata.trip;


import com.klosebros.kata.exception.UserNotLoggedInException;
import com.klosebros.kata.user.User;
import com.klosebros.kata.user.UserSession;

import java.util.ArrayList;
import java.util.List;

public class TripService {

	public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
		List<Trip> tripList = new ArrayList<Trip>();
		User loggedUser = getLoggedUser();
		boolean isLoggedUserFriend = false;
		if (loggedUser != null) {
			for (User friend : user.getFriends()) {
				if (friend.equals(loggedUser)) {
					isLoggedUserFriend = true;
					break;
				}
			}
			if (isLoggedUserFriend) {
				tripList = TripDAO.findTripsByUser(user);
			}
			return tripList;
		} else {
			throw new UserNotLoggedInException();
		}
	}

	User getLoggedUser() {
		return UserSession.getInstance().getLoggedUser();
	}

}
