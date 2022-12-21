package com.example.service.board.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.board.Board;
import com.example.repository.board.BoardMapper;
import com.example.service.board.BoardService;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardMapper boardMapper;

	/*
	 * 掲示板一覧を取得する
	 * 
	 */
	@Override
	public List<Board> selectBoardList() {
		return boardMapper.findBoardList();
	}
	
	/**
	 * board新規作成
	 * @param Board
	 * @return boolean
	 *
	 * */
	@Override
	public boolean saveBoard(Board board) {
		try {
			boardMapper.saveBoard(board);
			return true;
		} catch (Exception e) {
			log.error("boardの新規作成で例外が発生しました: {}", e);
			return false;
		}
	}

	/**
	 * board削除
	 * @param Board
	 * @return boolean
	 *
	 * */
	@Override
	public boolean deleteBoard(Board board) {
		try {
			boardMapper.deleteBoard(board);
			return true;
		} catch (Exception e) {
			log.error("boardの削除で例外が発生しました: {}", e);
			return false;
		}
	}

}
