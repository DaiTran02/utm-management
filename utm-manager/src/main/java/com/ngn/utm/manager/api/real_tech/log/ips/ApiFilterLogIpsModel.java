package com.ngn.utm.manager.api.real_tech.log.ips;

import lombok.Data;

@Data
public class ApiFilterLogIpsModel {
	private long fromDate;
	private long toDate;
	private String sourceIp;
	private String srcIp;
	private String srcPort;
	private String dstIp;
	private String dstPort;
	private String protocol;
	private String ipAddress;
	private String addressType;
	private int skip;
	private int limit;
	private String sigName;
}
