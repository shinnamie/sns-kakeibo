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

}
