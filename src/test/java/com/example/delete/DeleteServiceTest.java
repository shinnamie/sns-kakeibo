package com.example.delete;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.repository.kakeibo.KakeiboMapper;
import com.example.service.kakeibo.KakeiboService;

@SpringBootTest
public class DeleteServiceTest {
	
	@InjectMocks
	KakeiboService service;
	
	@Mock
	KakeiboMapper mapper;
	
	@Test
	@DisplayName("家計簿の削除テスト")
	void testKakeibodelete() throws Exception{
		
		mapper.delete(1L);
		Mockito.verify(mapper).delete(1L);
		
	}
}
