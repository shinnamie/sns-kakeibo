package com.example.service.board.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.board.Board;
import com.example.repository.board.BoardMapper;
import com.example.service.board.BoardService;

@Service
@Transactional
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardMapper boardMapper;

	@Override
	public List<Board> selectBoardList() {
		return boardMapper.findBoardList();
	}

}
