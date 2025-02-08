package com.ngn.utm.manager.api.pfsenses.utm_interface;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ApiReadInterfaceModel {
    private String name;
    private String descr;
    private String hwif;
    private boolean enable;
    @JsonProperty("if") 
    private String myif;
    private String status;
    private String macaddr;
    private Long mtu;
    private String ipaddr;
    private String subnet;
    private String linklocal;
    private Object ipaddrv6;
    private Object subnetv6;
    private Long inerrs;
    private Long outerrs;
    private Long collisions;
    private Long inbytespass;
    private Long outbytespass;
    private Long inpktspass;
    private Long outpktspass;
    private Long inbytesblock;
    private Long outbytesblock;
    private Long inpktsblock;
    private Long outpktsblock;
    private Long inbytes;
    private Long outbytes;
    private Long inpkts;
    private Long outpkts;
    private String media;
    private String gateway;
    private Object gatewayv6;
}
