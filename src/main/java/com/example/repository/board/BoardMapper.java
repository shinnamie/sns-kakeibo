package com.example.repository.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.domain.board.Board;

@Mapper
public interface BoardMapper {

	/*
	 * 掲示板一覧を取得する
	 * 
	 */
	public List<Board> findBoardList();

}
