package com.example.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.domain.Kakeibo;

@Mapper
public interface KakeiboMapper {
	
	/**
	 * 家計簿情報の全件取得
	 * 
	 * @return 検索結果
	 */
	List<Kakeibo> findAll();

}