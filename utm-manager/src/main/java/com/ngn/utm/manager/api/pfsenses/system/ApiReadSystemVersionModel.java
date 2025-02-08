package com.ngn.utm.manager.api.pfsenses.system;

import lombok.Data;

@Data
public class ApiReadSystemVersionModel {
	private String version;
	private String base;
	private String patch;
	private String buildtime;
	private int program;
}
