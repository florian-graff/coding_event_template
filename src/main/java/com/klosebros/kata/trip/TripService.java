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
		boolean isFriend = false;
		if (loggedUser != null) {
			isFriend = areUsersFriends(user, loggedUser);
			if (isFriend) {
				tripList = getByUser(user);
			}
			return tripList;
		} else {
			throw new UserNotLoggedInException();
		}
	}

	private static boolean areUsersFriends(User user, User loggedUser) {
		boolean isFriend = false;
		for (User friend : user.getFriends()) {
			if (friend.equals(loggedUser)) {
				isFriend = true;
				break;
			}
		}
		return isFriend;
	}

	protected List<Trip> getByUser(User user) {
		return TripDAO.findTripsByUser(user);
	}

	protected User getLoggedUser() {
		return UserSession.getInstance().getLoggedUser();
	}

}
