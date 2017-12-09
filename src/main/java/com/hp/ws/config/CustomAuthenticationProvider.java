package com.hp.ws.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.hp.ws.datasource.entity.AppUser;
import com.hp.ws.service.UserService;
import com.hp.ws.utility.CryptoUtil;

/**
 * @author 5019hoca
 *
 * 16 May 2017 14:43:28
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserService userService;
	
//	@Autowired
//	private RequestManager requestManager; 


	@Override
	public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
		
		
		String username = authentication.getName();
		String password = (String) authentication.getCredentials();
		if (username == null) {
			throw new BadCredentialsException("Please Enter Your User Name");
		}

		AppUser appUser = userService.getAppUser(username);
		if (appUser == null) {
			throw new BadCredentialsException("User Not Found :" + username);
		}
		if (!appUser.isEnabled()) {
			throw new BadCredentialsException("User Not Enabled :" + username);
		}

		String salt = new String(appUser.getSalt());
		CryptoUtil cryptoUtil = new CryptoUtil(salt.getBytes());
		String passwordDec = cryptoUtil.decrypt(appUser.getPassword(), appUser.getIv());

		if (passwordDec.equals(password)) {
			//requestManager.getRequestInfo().setAuthenticatedUser(appUser);
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			if (appUser.getRoleList() != null) {
				String[] roleList = appUser.getRoleList().split(";");
				for (String role : roleList) {
					SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
					authorities.add(authority);
				}
			}
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					authentication.getPrincipal(), authentication.getCredentials(), authorities);
			return authenticationToken;

		} else {
			throw new BadCredentialsException("Wrong password.");
		}
		
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
		// return
		// authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}