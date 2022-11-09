package com.example.findAll;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.domain.kakeibo.Kakeibo;
import com.example.service.kakeibo.KakeiboService;




@SpringBootTest
class FindKakeiboListServiceTest {

	@Autowired
	KakeiboService service;
	
	@Test
	@DisplayName("家計簿一覧表示のテスト")
	void testKakeiboList() throws Exception {
		List<Kakeibo> kakeiboList = service.findKakeiboList();
	        int expect = kakeiboList.size();
	        int actual = kakeiboList.size();
	        assertEquals(expect, actual);
	}

}
