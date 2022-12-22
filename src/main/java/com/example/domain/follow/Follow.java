package com.example.domain.follow;

import com.example.domain.user.User;

import lombok.Data;

@Data
public class Follow {

	private long followingId;
	private long followedId;
	private User user;
}
