package com.example.domain.board;

import com.example.domain.user.User;

import lombok.Data;

@Data
public class Board {

	private Long id;
	private String name;
	private String description;
	private User user;

}
