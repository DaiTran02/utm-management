package com.ngn.utm.manager.api.real_tech.log.login_web;

import lombok.Data;

@Data
public class ApiLoginWebModel {
	private String id;
    private long date;
    private String sourceIp;
    private String sourceName;
    private String userName;
    private String ipAddress;
    private String path;
    private String database;
    private String result;
}
