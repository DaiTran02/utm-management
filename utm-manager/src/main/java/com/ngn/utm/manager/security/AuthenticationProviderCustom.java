package com.ngn.utm.manager.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.ngn.utm.manager.api.ApiResultResponse;
import com.ngn.utm.manager.api.real_tech.authen.ApiAuthenUtil;
import com.ngn.utm.manager.api.real_tech.authen.ApiInputAuthenModel;
import com.ngn.utm.manager.api.real_tech.authen.ApiUserRealTechModel;
import com.ngn.utm.manager.utils.SessionUtil;

@Component
public class AuthenticationProviderCustom implements AuthenticationProvider{
	
	private final ApiAuthenUtil apiAuthenUtil;
	
	public AuthenticationProviderCustom(ApiAuthenUtil apiAuthenUtil) {
		this.apiAuthenUtil = apiAuthenUtil;
	}
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		System.out.println("It change");
		String userName = authentication.getName();
		String password = authentication.getCredentials().toString();
		ApiInputAuthenModel inputUser = new ApiInputAuthenModel();
		inputUser.setUsername(userName);
		inputUser.setPassword(password);
		try {
			ApiResultResponse<ApiUserRealTechModel> dataUser = apiAuthenUtil.login(inputUser);
			System.out.println("Data ne: "+dataUser);
			if(dataUser.isSuccess()) {
				ApiUserRealTechModel user = dataUser.getData();
				
				UserDetailsCustom userDetailsCustom = new UserDetailsCustom();
				userDetailsCustom.setUser(user);
				SessionUtil.setToken(user.getToken());
				
				
				return new UsernamePasswordAuthenticationToken(userDetailsCustom, password,userDetailsCustom.getAuthorities());
			}else {
				System.out.println("OH NO");
			}
		} catch (Exception e) {
			System.out.println("OH NO");
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
