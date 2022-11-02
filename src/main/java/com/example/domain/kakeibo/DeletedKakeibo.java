package com.example.domain.kakeibo;

import java.sql.Timestamp;

public class DeletedKakeibo {

	/** 家計簿論理削除Id */
	private long id;
	/** 家計簿Id */
	private long kakeiboId;
	/** 削除日時Id */
	private Timestamp deleteAt;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getKakeiboId() {
		return kakeiboId;
	}

	public void setKakeiboId(long kakeiboId) {
		this.kakeiboId = kakeiboId;
	}

	public Timestamp getDeleteAt() {
		return deleteAt;
	}

	public void setDeleteAt(Timestamp deleteAt) {
		this.deleteAt = deleteAt;
	}

	@Override
	public String toString() {
		return "DeletedKakeibo [id=" + id + ", kakeiboId=" + kakeiboId + ", deleteAt=" + deleteAt + "]";
	}

}
