package com.example.domain.post;

import java.time.LocalDateTime;

import com.example.domain.board.Board;
import com.example.domain.user.User;

import lombok.Data;

@Data
public class Post {

	/** 主キー */
	private Long id;
	/** 内容 */
	private String contents;
	/** 投稿日 */
	private LocalDateTime insertAt;
	/** 更新日 */
	private LocalDateTime updateAt;
	/** ユーザー情報 */
	private User user;
	/** 掲示板情報 */
	private Board board;

}
