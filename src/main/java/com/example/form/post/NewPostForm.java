package com.example.form.post;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class NewPostForm {

	// postのコンテンツ
	@NotBlank
	@Length(max=280)
	private String contents;

	// 掲示板のID
	@NotNull
	private Long boardId;

	// ログインしているuserのID
	@NotNull
	private Long userId;

}
