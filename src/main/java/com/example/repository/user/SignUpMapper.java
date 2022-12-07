package com.example.repository.user;

import org.apache.ibatis.annotations.Mapper;

import com.example.domain.user.User;

@Mapper
public interface SignUpMapper {

	public boolean signUp(User user) throws Exception;

}
