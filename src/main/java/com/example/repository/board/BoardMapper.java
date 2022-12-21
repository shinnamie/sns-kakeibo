package com.example.repository.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.domain.board.Board;
import com.example.domain.post.Post;

@Mapper
public interface BoardMapper {

	/*
	 * 掲示板一覧を取得する
	 * 
	 */

	public List<Board> findBoardList();
	
	/**
	 * board新規作成
	 * @param Board
	 * @return boolean
	 *
	 * */
	public boolean saveBoard(Board board) throws Exception;

	/**
	 * board削除
	 * @param Board
	 * @return boolean
	 *
	 * */
	public boolean deleteBoard(Board board) throws Exception;

}
