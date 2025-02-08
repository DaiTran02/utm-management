package com.ngn.utm.manager.api.real_tech.host;

import lombok.Data;

@Data
public class ApiHostModel {
	private int id;
	private String hostname;
	private String ipAddress;
	private int mntPort;
	private String clientId;
	private String clientToken;
	private boolean useConfigModule;
	private boolean useLogModule;
	private boolean isConnect = true;
}
