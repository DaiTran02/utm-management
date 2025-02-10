package com.ngn.utm.manager.api.real_tech.log.config_change;

import lombok.Data;

@Data
public class ApiLogConfigChangeModel {
	private String id;
    private long date;
    private String sourceIp;
    private String sourceName;
    private String userName;
    private String reason;
    private String path;
    private String ipAddress;
    private String database;
}
