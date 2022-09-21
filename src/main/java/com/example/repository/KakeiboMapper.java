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
	 * 家計簿idから家計簿情報を取得
	 * 
	 * @return 検索結果(1件)
	 */
	Kakeibo findByKakeiboId(Integer id);

	/**
	 * 家計簿の新規登録
	 * 
	 * @param kakeibo
	 */
	void save(Kakeibo kakeibo);

	/**
	 * 家計簿の編集
	 * 
	 * @param kakeibo
	 */
	void update(Kakeibo kakeibo);

	/**
	 * 論理削除フラグ
	 * 
	 * @param kakeibo 家計簿
	 */
	void updateIsDelete(Kakeibo kakeibo);

	/**
	 * 家計簿の論理削除
	 * 
	 * @param deletedKakeibo 家計簿論理削除データ
	 */
	void delete(DeletedKakeibo deletedKakeibo);

}
