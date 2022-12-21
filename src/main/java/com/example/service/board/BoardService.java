package com.example.service.board;

import java.util.List;

import com.example.domain.board.Board;
import com.example.domain.post.Post;

public interface BoardService {

	public List<Board> selectBoardList();
	
	public boolean saveBoard(Board board);
	
	public boolean deleteBoard(Board board);

}
