package com.ngn.utm.manager.api.real_tech.log.create_group;

import lombok.Data;

@Data
public class ApiLogCreateGroupFilterModel {
	private Long fromDate;
	private Long toDate;
	private String sourceIp;
	private String groupIp;
	private String action;
	private int skip;
	private int limit;
}
