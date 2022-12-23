package com.example.form.board;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class DeleteBoardForm {
	
	// boardのID
	@NotNull
	public Long boardId;
	// 削除するUserのID
	@NotNull
	public Long userId;

}
