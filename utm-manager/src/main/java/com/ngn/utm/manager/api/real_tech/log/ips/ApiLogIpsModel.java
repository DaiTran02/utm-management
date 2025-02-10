package com.ngn.utm.manager.api.real_tech.log.ips;

import lombok.Data;

@Data
public class ApiLogIpsModel {
	private String id;
    private String sourceIp;
    private String sourceName;
    private long date;
    private SrcIpModel srcIp;
    private int srcPort;
    private DstIpModel dstIp;
    private int dstPort;
    private String protocol;
    private String sigName;
    private int sigId;
    private int sigRev;
    private int priority;
    private int genId;
    private String classification;
	
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
