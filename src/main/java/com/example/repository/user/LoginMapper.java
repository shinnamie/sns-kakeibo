package com.example.repository.user;

import org.apache.ibatis.annotations.Mapper;

import com.example.domain.user.User;

@Mapper
public interface LoginMapper {

	User Login(String mailAddress, String password);

}
