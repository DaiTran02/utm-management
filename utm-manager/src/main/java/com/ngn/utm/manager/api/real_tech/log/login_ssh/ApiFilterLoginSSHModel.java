package com.ngn.utm.manager.api.real_tech.log.login_ssh;

import lombok.Data;

@Data
public class ApiFilterLoginSSHModel {
	private long fromDate;
	private long toDate;
	private String sourceIp;
	private int skip;
	private int limit;
	private String fromIp;
	private String portNumber;
	private String protocol;
	private String method;
	private String result;
}
