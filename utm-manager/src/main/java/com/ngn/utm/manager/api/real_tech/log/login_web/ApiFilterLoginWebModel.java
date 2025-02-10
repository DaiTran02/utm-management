package com.ngn.utm.manager.api.real_tech.log.login_web;

import lombok.Data;

@Data
public class ApiFilterLoginWebModel {
	private long fromDate;
	private long toDate;
	private String sourceIp;
	private int skip;
	private int limit;
	private String result;
	private String userName;
	private String ipAddress;
	private String path;
	private String dataBase;
}
