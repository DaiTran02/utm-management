package com.ngn.utm.manager.api.real_tech.host;

import lombok.Data;

@Data
public class ApiConfigModel {
	private int id;
	private String fileName;
	private String md5Hash;
	private long createdDate;
}
