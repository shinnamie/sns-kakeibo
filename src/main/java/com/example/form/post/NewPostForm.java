package com.example.form.post;

import lombok.Data;

@Data
public class NewPostForm {

	// postのコンテンツ
	private String contents;

	// 掲示板のID
	private Long boardId;

	// ログインしているuserのID
	private Long userId;

}
