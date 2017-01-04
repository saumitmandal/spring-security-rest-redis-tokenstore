package com.personal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.stereotype.Service;

@Service
public class RedisUserRepositoryService {

	@Autowired
	private RedisConnectionFactory redisConnectionFactory;

	RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
	
	public String getUser(String userName){
		return "";
	}
			
	
}
