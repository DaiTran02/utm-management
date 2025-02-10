package com.ngn.utm.manager.api.real_tech.log.create_user;

import lombok.Data;

@Data
public class ApiFilterLogCreateUserModel {
	private Long fromDate;
	private Long toDate;
	private String sourceIp;
	private String groupIp;
	private String action;
	private int skip;
	private int limit;
}
