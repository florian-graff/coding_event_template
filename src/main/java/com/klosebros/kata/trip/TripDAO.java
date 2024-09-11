package com.klosebros.kata.trip;


import com.klosebros.kata.exception.CollaboratorCallException;
import com.klosebros.kata.user.User;

import java.util.List;

public class TripDAO {

	public static List<Trip> findTripsByUser(User user) {
		throw new CollaboratorCallException(
				"TripDAO should not be invoked on an unit test.");
	}
	
}