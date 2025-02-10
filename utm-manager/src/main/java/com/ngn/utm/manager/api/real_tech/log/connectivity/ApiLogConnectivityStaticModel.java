package com.ngn.utm.manager.api.real_tech.log.connectivity;

import lombok.Data;

@Data
public class ApiLogConnectivityStaticModel {
	private String srcIp;
	private String dstIp;
	private int dstPort;
	private String protocol;
    private String action;
    private int count;
}
