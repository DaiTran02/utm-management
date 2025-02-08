package com.ngn.utm.manager.api.pfsenses.alias;

import lombok.Data;

@Data
public class ApiReadFirewallAliasModel {
	private String name;
	private String address;
	private String descr;
	private String type;
	private String detail;
	
	public String getAddressText() {
		String text = address;
		text = text.replace(" ", " , ");
		return text;
	}
}
