package com.ngn.utm.manager.utils;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class PropUtils implements EnvironmentAware{
	private static Environment env;
	
	public static Object getProperty(String key) {
		return env.getProperty(key);
	}
	
	@Override
	public void setEnvironment(Environment environment) {
		env=environment;
	}
	
	public static String getCoreRealTechUrl() {
		return getProperty("apiUrl").toString();
	}

}
