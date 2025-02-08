package com.ngn.utm.manager.api.pfsenses.config;

import lombok.Data;

@Data
public class ApiConfigModel {
	private int id;
	private String fileName;
	private String md5Hash;
	private long createdDate;
}
