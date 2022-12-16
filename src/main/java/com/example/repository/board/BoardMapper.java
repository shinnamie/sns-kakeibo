package com.example.repository.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.domain.board.Board;

@Mapper
public interface BoardMapper {

	public List<Board> findBoardList();

}
