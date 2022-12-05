package com.example.service.user;

import com.example.domain.user.User;

public interface LoginService {

	User login(String mailAddress, String password);

}
