package com.example.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.domain.DeletedKakeibo;
import com.example.domain.Kakeibo;

@Mapper
public interface KakeiboMapper {
	
	/**
	 * 家計簿情報の全件取得
	 * 
	 * @return 検索結果
	 */
	List<Kakeibo> findAll();

	/**
	 * 家計簿の新規登録
	 * 
	 * @param kakeibo
	 */
	void save(Kakeibo kakeibo);

	/**
	 * 家計簿の論理削除
	 * 
	 * @param 家計簿論理削除データ
	 */
	void delete(DeletedKakeibo deletedKakeibo);

}
