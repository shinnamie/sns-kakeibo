package com.example.form.board;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class NewBoardForm {

	// 掲示板名
	@NotBlank
	@Length(max = 100, min = 1)
	private String boardName;
	// 掲示板の説明
	@Length(max = 100)
	private String description;
	// 作成したUserのID
	@NotNull
	private Long userId;

}
