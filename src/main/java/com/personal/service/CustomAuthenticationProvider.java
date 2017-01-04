package com.personal.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import hello.data.User;
import hello.data.UserRepository;

@Service
public class CustomAuthenticationProvider implements AuthenticationProvider, Serializable {

	@Autowired
	private UserRepository userRepository;

	private boolean validateUserName(String userName) {
		User user = userRepository.findByLogin(userName);
		return user != null;
	}

	@SuppressWarnings("serial")
	private static Map<String, String> SIMPLE_USERS = new HashMap<String, String>(2) {
		{
			put("joe", "joe");
			put("bob", "bob");
			put("sam", "sam");
		}
	};

	@SuppressWarnings("serial")
	private static List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>(1) {
		{
			add(new GrantedAuthority() {

				@Override
				public String getAuthority() {
					return "USER";
				}
			});
		}
	};

	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		if (SIMPLE_USERS.containsKey(auth.getPrincipal())
				&& SIMPLE_USERS.get(auth.getPrincipal()).equals(auth.getCredentials())) {
			return new UsernamePasswordAuthenticationToken(auth.getName(), auth.getCredentials(), AUTHORITIES);
		}
		throw new BadCredentialsException("Username/Password does not match for " + auth.getPrincipal());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}
}
