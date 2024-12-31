package com.ngn.utm.manager.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.ngn.utm.manager.api.ApiResultResponse;
import com.ngn.utm.manager.api.real_tech.authen.ApiAuthenUtil;
import com.ngn.utm.manager.api.real_tech.authen.ApiUserRealTechModel;
import com.ngn.utm.manager.utils.SessionUtil;

@Component
public class AuthenticationProviderCustom implements AuthenticationProvider{
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String userName = authentication.getName();
		String password = authentication.getCredentials().toString();
		ApiUserRealTechModel inputUser = new ApiUserRealTechModel();
		inputUser.setUsername(userName);
		inputUser.setPassword(password);
		try {
			ApiResultResponse<ApiUserRealTechModel> dataUser = ApiAuthenUtil.loginStatic(inputUser);
			if(dataUser.isSuccess()) {
				
				ApiUserRealTechModel user = dataUser.getResult();
				
				UserDetailsCustom userDetailsCustom = new UserDetailsCustom();
				userDetailsCustom.setUser(user);
				SessionUtil.setToken(user.getToken());
			}
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
