package com.xprod.rest.listener;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;

import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import com.xprod.rest.entity.User;
import com.xprod.rest.service.LoginAttemptService;


@Component
public abstract class AuthenticationSuccessListener {

	private LoginAttemptService loginAttemptService;
	
	@Autowired
	public AuthenticationSuccessListener(LoginAttemptService loginAttemptService) {
		super();
		this.loginAttemptService = loginAttemptService;
	}
	
	@EventListener
	public void onAuthenticationSuccess(AuthenticationSuccessEvent event) throws ExecutionException {
		Object principal = event.getAuthentication().getPrincipal();
		if(principal instanceof User) {
			User user = (User) event.getAuthentication().getPrincipal();
			loginAttemptService.evictUserFromLoginAttemptCache(user.getUsername());
		}		
	}
}
