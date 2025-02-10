package com.ngn.utm.manager.api.real_tech.log.ips;

import lombok.Data;

@Data
public class ApiLogIpsStaticModel {
	private String srcIp;
	private String dstIp;
	private int dstPort;
	private String protocol;
    private String sigName;
    private int count;
}
