package com.example.repository.board;

import org.apache.ibatis.annotations.Mapper;

import com.example.domain.board.Board;

@Mapper
public interface BoardMapper {

	public Board findBoardList();

}
