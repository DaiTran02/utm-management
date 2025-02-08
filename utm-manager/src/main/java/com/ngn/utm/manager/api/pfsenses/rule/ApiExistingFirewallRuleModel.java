package com.ngn.utm.manager.api.pfsenses.rule;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ApiExistingFirewallRuleModel {
	private String id;
	private String tracker;
	private String type;
	private String myinterface;
	private String ipprotocol;
	private String icmptype;
	private String tag;
	private String tagged;
	private String max;
	@JsonProperty("max-src-nodes")
	private String maxsrcnodes;
	
	@JsonProperty("max-src-conn")
	private String maxsrcconn;
	
	@JsonProperty("max-src-states")
	private String maxsrcstates;

	private String statetimeout;
	private String statetype;
	private String os;
	private Object source;
	private Destination destination;
	private String descr;
	private Updated updated;
	private Created created;
	
	@Data
	public class Source{
		private String any;
	}
	
	@Data
	public class Destination{
		private String any;
		private String network;
		private String port;
	}
	
	@Data
	public class Updated{
		private String time;
	    private String username;
	}
	
	@Data
	public class Created{
		private String time;
		private String username;
	}
}
