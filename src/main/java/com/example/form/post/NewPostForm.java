package com.example.form.post;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class NewPostForm {

	// postのコンテンツ
	@NotNull
	private String contents;

	// 掲示板のID
	@NotNull
	private Long boardId;

	// ログインしているuserのID
	@NotNull
	private Long userId;

}
