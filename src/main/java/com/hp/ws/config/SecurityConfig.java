package com.hp.ws.config;

import java.util.Arrays;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//@Configuration
//@EnableWebSecurity
/**
 * @author 5019hoca
 *
 * 16 May 2017 14:43:34
 */
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		 return new ProviderManager(Arrays.asList((AuthenticationProvider) new CustomAuthenticationProvider()));
	}

}