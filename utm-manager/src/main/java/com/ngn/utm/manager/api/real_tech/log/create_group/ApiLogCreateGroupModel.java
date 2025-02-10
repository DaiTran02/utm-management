package com.ngn.utm.manager.api.real_tech.log.create_group;

import lombok.Data;

@Data
public class ApiLogCreateGroupModel {
	private String id;
    private long date;
    private String sourceIp;
    private String sourceName;
    private String groupName;
    private String action;
}
