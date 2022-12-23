package com.example.form.post;

import lombok.Data;

@Data
public class DeletePostForm {
	
	// postのID
	private Long postId;
	
	// 掲示板のID
	private Long boardId;
	
	// ログインしているuserのID
	private Long userId;

}
