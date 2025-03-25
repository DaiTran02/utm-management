package com.ngn.utm.manager.utils;

import java.util.HashSet;
import java.util.Set;

import com.ngn.utm.manager.api.real_tech.host.ApiHostModel;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.WrappedSession;

public class SessionUtil {
	public static final String TOKEN = "TOKEN";
	public static final String USER = "USER";
	public static final String DEVICE_INFO = "DEVICE_INFO";
	public static final String VERSION = "VERSION";
	
	public static void setToken(String token) {
		try {
			getSession().setAttribute(TOKEN, token);
		} catch (Exception e) {
			
		}
	}
	
	public static String getToken() {
		try {
			return (String) getAttribute(TOKEN);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static void setDeviceInfo(ApiHostModel hostModel) {
		try {
			getSession().setAttribute(DEVICE_INFO, hostModel);
		} catch (Exception e) {
		}
	}
	
	public static ApiHostModel getDeviceInfo() {
		try {
			return (ApiHostModel) getAttribute(DEVICE_INFO);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static void setVersion(String version) {
		try {
			getSession().setAttribute(VERSION, version);
		} catch (Exception e) {
		}
	}
	
	public static String getVersion() {
		try {
			return (String) getSession().getAttribute(VERSION);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static WrappedSession getSession() {
		return VaadinService.getCurrentRequest().getWrappedSession();
	}

	public static Object getAttribute(String attribute) {
		return getSession().getAttribute(attribute);
	}

	public static void setAttribute(String key, Object value) {
		getSession().setAttribute(key, value);
	}

	public static void removeAttributes(String... keys) {
		for (String key : keys) {
			getSession().removeAttribute(key);
		}
	}
	
	public static void cleanAllSession() {
		Set<String> attributeNames = new HashSet<>(getSession().getAttributeNames());
		attributeNames.forEach(getSession()::removeAttribute);
	}
}
