package com.ngn.utm.manager.utils;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class PropUtils {
	private final Environment env;
	
	public PropUtils(Environment env) {
		this.env = env;
	}
	
    public String getProperty(String key) {
        return env.getProperty(key);
    }

    public String getCoreRealTechUrl() {
        String apiUrl = getProperty("apiUrl");
        return apiUrl != null ? apiUrl : "";
    }
    
    public String getVersionUtm() {
    	String utmVersion = getProperty("utm.version.api");
    	return utmVersion != null ? utmVersion : "";
    }
    
}
