package com.example.domain.user;

import java.util.List;

import com.example.domain.kakeibo.Kakeibo;

import lombok.Data;

@Data
public class User {

	private Long id;
	private String mailAddress;
	private String password;
	private String name;
	private Integer gender;
	private String remarks;
	private List<Kakeibo> kakeiboList;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMailAddress() {
		return mailAddress;
	}
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public List<Kakeibo> getKakeiboList() {
		return kakeiboList;
	}
	public void setKakeiboList(List<Kakeibo> kakeiboList) {
		this.kakeiboList = kakeiboList;
	}
	
	

}
