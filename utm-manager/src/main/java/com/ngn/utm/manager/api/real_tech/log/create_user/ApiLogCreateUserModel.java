package com.ngn.utm.manager.api.real_tech.log.create_user;

import lombok.Data;

@Data
public class ApiLogCreateUserModel {
	private String id;
    private long date;
    private String sourceIp;
    private String sourceName;
    private String userName;
    private String action;
}
