package com.example.service.board.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.board.Board;
import com.example.service.board.BoardService;

@Service
@Transactional
public class BoardServiceImpl implements BoardService {

	@Override
	public List<Board> selectBoardList() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
