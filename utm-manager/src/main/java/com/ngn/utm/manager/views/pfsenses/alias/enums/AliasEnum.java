package com.ngn.utm.manager.views.pfsenses.alias.enums;

public enum AliasEnum{
	HOST("host","Host(s)"),
	NETWORK("network","Network(s)"),
	PORT("port","Port(s)"),
	URL("url","URL (IP)"),
	URL_PORTS("url_ports","URL (Port)"),
	URLTABLE("urltable","URL Table (IP)"),
	URLTABLE_PORTS("urltable_ports","URL Table (Port)");


	private String key;
	private String title;

	private AliasEnum(String key,String title) {
		this.key = key;
		this.title = title;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public static AliasEnum findByKey(String key) {
		if(key == null) {
			return null;
		}
		
		for(AliasEnum alias : AliasEnum.values()) {
			if(alias.getKey().equals(key)) {
				return alias;
			}
		}
		return null;
	}

}