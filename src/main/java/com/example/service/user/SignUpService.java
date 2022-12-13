package com.example.service.user;

import com.example.domain.user.User;

public interface SignUpService {

	boolean signUp(User user);
	
	User findByEmail(String mailAddress);

}
