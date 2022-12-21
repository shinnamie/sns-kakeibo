package com.example.form.post;

import lombok.Data;

@Data
public class DeletePostForm {
	
	// postのID
	private Long id;
	
	// 掲示板のID
	private Long boardId;

}
