package com.example.domain.user;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class User implements Serializable {

	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 名前
	 */
	private String name;
	/**
	 * メールアドレス
	 */
	private String email;
	/**
	 * パスワード
	 */
	private String password;
	/**
	 * 住所
	 */
	private String address;
	/**
	 * 電話番号
	 */
	private String phone;
	/**
	 * 更新日時
	 */
	private Date updateDate;
	/**
	 * 登録日時
	 */
	private Date createDate;
	/**
	 * 削除日時
	 */
	private Date deleteDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getDeleteDate() {
		return deleteDate;
	}

	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", address="
				+ address + ", phone=" + phone + ", updateDate=" + updateDate + ", createDate=" + createDate
				+ ", deleteDate=" + deleteDate + "]";
	}

}
