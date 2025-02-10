package com.ngn.utm.manager.api.real_tech.log.config_change;

import lombok.Data;

@Data
public class ApiFilterLogConfigChangeModel {
	private Long fromDate;
	private Long toDate;
	private String sourceIp;
	private String username;
	private String reason;
	private String path;
	private String ipAddress;
	private String database;
	private int skip;
	private int limit;
}
