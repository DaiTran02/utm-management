package com.ngn.utm.manager.api.real_tech.log.login_ssh;

import lombok.Data;

@Data
public class ApiLoginSSHModel {
	private String id;
    private long date;
    private String sourceIp;
    private String sourceName;
    private String fromIp;
    private String portNumber;
    private String protocol;
    private String method;
    private String result;
}
