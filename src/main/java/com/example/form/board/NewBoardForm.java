package com.example.form.board;

import com.example.domain.user.User;

import lombok.Data;

@Data
public class NewBoardForm {

	private String name;
	private String description;
	private User user;

}
