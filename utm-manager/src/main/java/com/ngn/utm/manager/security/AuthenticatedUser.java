package com.ngn.utm.manager.security;

import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.ngn.utm.manager.api.real_tech.authen.ApiUserRealTechModel;
import com.ngn.utm.manager.utils.SessionUtil;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.spring.security.AuthenticationContext;

import jakarta.servlet.ServletException;

@Component
public class AuthenticatedUser {
	 private final AuthenticationContext authenticationContext;

	    public AuthenticatedUser(AuthenticationContext authenticationContext) {
	        this.authenticationContext = authenticationContext;
	    }
	    
	    public Optional<ApiUserRealTechModel> get() {
	    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    	if(principal instanceof UserDetailsCustom) {
	    		UserDetailsCustom userDetailsCustom= (UserDetailsCustom) principal;
	        	return Optional.ofNullable(userDetailsCustom.getUser());
	    	}
	    	return Optional.empty();
	    }

	    public void logout() {
	    	SessionUtil.cleanAllSession();
	        authenticationContext.logout();
	    }

	    public boolean isAuthenticated() {
	        VaadinServletRequest request = VaadinServletRequest.getCurrent();
	        return request != null && request.getUserPrincipal() != null;
	    }

	    public boolean authenticate(String username, String password) {
	        VaadinServletRequest request = VaadinServletRequest.getCurrent();
	        if (request == null) {
	            return false;
	        }
	        try {
	            request.login(username, password);
	            return true;
	        } catch (ServletException e) {
	            return false;
	        }
	    }
}
