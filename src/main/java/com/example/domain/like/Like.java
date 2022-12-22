package com.example.domain.like;

import java.sql.Timestamp;

import com.example.domain.board.Board;
import com.example.domain.post.Post;
import com.example.domain.user.User;

import lombok.Data;

@Data
public class Like {

	// いいねしたUser
	public User user;

	// いいねしたPost
	public Post post;

	// いいねしたPostがあるBoard
	public Board board;

	// いいねしたTime
	private Timestamp insertAt;

}
