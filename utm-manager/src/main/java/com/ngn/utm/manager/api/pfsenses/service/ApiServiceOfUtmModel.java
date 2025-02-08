package com.ngn.utm.manager.api.pfsenses.service;

import lombok.Data;

@Data
public class ApiServiceOfUtmModel {
	private String name;
	private String description;
	private boolean enabled;
	private String status;
	private String service;
}
