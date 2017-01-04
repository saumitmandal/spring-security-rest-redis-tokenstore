/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import com.personal.service.MyAuthenticationProvider;

import hello.data.UserRepository;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

//	@Autowired
//	private CustomUserDetailsService userDetailsService;
	
	//@Autowired
    //private MyAuthenticationProvider authenticationProvider = new MyAuthenticationProvider();
	
	
	
	
    private MyAuthenticationProvider authenticationProvider = new MyAuthenticationProvider();
    
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http
//        .csrf().disable()
//        .anonymous().disable()
//        .authorizeRequests()
//        .antMatchers("/oauth/token").permitAll();
    	
    	http
        .httpBasic().and()
        .authorizeRequests()
          .antMatchers(HttpMethod.GET, "/greeting").hasRole("ADMIN")
          .antMatchers(HttpMethod.POST, "/greeting").hasRole("ADMIN")
          .antMatchers(HttpMethod.PUT, "/greeting/**").hasRole("ADMIN")
          .antMatchers(HttpMethod.PATCH, "/greeting/**").hasRole("ADMIN").and()
        .csrf().disable();
    }
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		System.out.println("\n \n \n \n WebSecurityConfiguration called");
		 //auth.userDetailsService(userDetailsService);
		auth.authenticationProvider(authenticationProvider);
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		System.out.println("\n\n\n AuthenticationManager authenticationManagerBean called....");
		return super.authenticationManagerBean();
	}

	
	@Autowired
	private RedisConnectionFactory redisConnectionfactory;
	
	@Bean
    public TokenStore tokenStore() {
		System.out.println("\n\n\nToken Store configured with redis\n\n");
        return new RedisTokenStore(this.redisConnectionfactory);
    }
	
	
//	@Autowired
//    private ClientDetailsService clientDetailsService;
//	
//	@Bean
//    @Autowired
//    public TokenStoreUserApprovalHandler userApprovalHandler(TokenStore tokenStore){
//		System.out.println("\n\n\nTokenStoreUserApprovalHandler userApprovalHandler\n\n\n\n");
//        TokenStoreUserApprovalHandler handler = new TokenStoreUserApprovalHandler();
//        handler.setTokenStore(tokenStore);
//        handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
//        handler.setClientDetailsService(clientDetailsService);
//        return handler;
//    }
//     
    @Bean
    @Autowired
    public ApprovalStore approvalStore(TokenStore tokenStore) throws Exception {
    	System.out.println("\n\n\nApprovalStore approvalStore\n\n\n");
        TokenApprovalStore store = new TokenApprovalStore();
        store.setTokenStore(tokenStore);
        return store;
    }
}
