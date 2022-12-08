package com.example.service.post.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.post.Post;
import com.example.repository.post.PostMapper;
import com.example.service.post.PostService;

@Service
@Transactional
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostMapper postMapper;
	
	@Override
	public List<Post> selectPostList(Long boardId) {
		return postMapper.findPostList(boardId);
	}
	
	/*
	 * 型変換
	 * Timestamp型 →　String型(yyyy/MM/dd HH:mm:ss 表示)
	 */
	public String changeType(LocalDateTime localDateTime) {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		String stringTime = dtf.format((TemporalAccessor) localDateTime);
		
		return stringTime;
	}

}
