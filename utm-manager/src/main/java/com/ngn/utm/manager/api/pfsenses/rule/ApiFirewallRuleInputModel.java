package com.ngn.utm.manager.api.pfsenses.rule;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ApiFirewallRuleInputModel {
	private String ackqueue;
    private boolean apply;
    private String defaultqueue;
    private String descr;
    private String direction;
    private boolean disabled;
    private String dnpipe;
    private String dst;
    private String dstport;
    private boolean floating;
    private String gateway;
    private List<String> icmptype;
    @JsonProperty("interface") 
    private List<String> myinterface;
    private String ipprotocol;
    private boolean log;
    private String pdnpipe;
    private String protocol;
    private boolean quick;
    private String sched;
    private String src;
    private String srcport;
    private String statetype;
    private boolean tcpflags_any;
    private List<String> tcpflags1;
    private List<String> tcpflags2;
    private boolean top;
    private int tracker;
    private String type;
}
