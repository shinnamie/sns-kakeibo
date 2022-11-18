package com.example.findAll;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.domain.kakeibo.Kakeibo;
import com.example.repository.kakeibo.KakeiboMapper;
import com.example.service.kakeibo.KakeiboService;




@SpringBootTest
class FindKakeiboListServiceTest {

	@InjectMocks
	KakeiboService service;

	@Mock
	KakeiboMapper mapper;

	@Test
	@DisplayName("家計簿一覧表示のサービスクラステスト（正常）")
	void testKakeiboList() throws Exception {
		List<Kakeibo> kakeiboList = mapper.findAll();

		//正常処理
		assertThat(kakeiboList).isNotNull();   
	}

	@Test
	@DisplayName("家計簿一覧表示のサービスクラステスト（異常）")
	void testKakeiboList2() throws Exception {
		List<Kakeibo> kakeiboList2 = null;

		//異常処理
		assertThat(kakeiboList2).isNotNull();
	}
}
