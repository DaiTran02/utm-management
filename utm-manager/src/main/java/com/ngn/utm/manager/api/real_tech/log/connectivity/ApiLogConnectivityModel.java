package com.ngn.utm.manager.api.real_tech.log.connectivity;

import lombok.Data;

@Data
public class ApiLogConnectivityModel {
	private String id;
    private String sourceIp;
    private String sourceName;
    private long date;
    private SrcIpModel srcIp;
    private int srcPort;
    private DstIpModel dstIp;
    private int dstPort;
    private String protocol;
    private String action;
    private String urg;
    private int ttl;
    private int tracker;
    private String tos;
    private int tcpWindow;
    private String tcpOptions;
    private String tcpFlags;
    private String subRuleNumber;
    private long sequenceNumber;
    private int ruleNumber;
    private String reason;
    private String realInterface;
    private int protocolId;
    private int packetLength;
    private int packetId;
    private int offset;
    private int ipVersion;
    private String flag;
    private String ecn;
    private String direction;
    private int dataLength;
    private String anchor;
    private String ackNumber;
    
    @Data
    public static class DstIpModel {
    	private String ipAddress;
        private String countryCode;
        private String countryName;
        private String regionName;
        private String cityName;
        private String zipCode;
        private String timeZone;
        private String flag;
        private double latitude;
        private double longitude;
    }
    
    @Data
    public static class SrcIpModel {
    	private String ipAddress;
        private String countryCode;
        private String countryName;
        private String regionName;
        private String cityName;
        private String zipCode;
        private String timeZone;
        private String flag;
        private double latitude;
        private double longitude;
    }
}
