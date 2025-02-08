package com.ngn.utm.manager.api.pfsenses.gateway;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ApiReadGateStatusModel {
	private String id;
	private String action_disable;
	private String alert_interval;
	private String apply;
	private String data_payload;
	private String descr;
	private boolean disabled;
	private String force_down;
	private String gateway;
	
	@JsonProperty("interface")
	private String myinterface;
	
	private String interval;
	private String ipprotocol;
	private String latencyhigh;
	private String latencylow;
	private String loss_interval;
	private String losshigh;
	private String losslow;
	private String monitor;
	private String monitor_disable;
	private String name;
	private String time_period;
	private String weight;
	private String tiername;
	private String attribute;
	private String isdefaultgw;
}
