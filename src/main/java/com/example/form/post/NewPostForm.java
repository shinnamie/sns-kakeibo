package com.example.form.post;

import lombok.Data;

@Data
public class NewPostForm {

	private Long userId;
	private Long boardId;
	private String contents;

}
