package com.ngn.utm.manager.api.real_tech.log.connectivity;

import lombok.Data;

@Data
public class ApiFilterLogConnectivityModel {
	private long fromDate;
	private long toDate;
	private String sourceIp;
	private String srcIp;
	private String srcPort;
	private String dstIp;
	private String dstPort;
	private String protocol;
	private String action;
	private int skip;
	private int limit;
}
