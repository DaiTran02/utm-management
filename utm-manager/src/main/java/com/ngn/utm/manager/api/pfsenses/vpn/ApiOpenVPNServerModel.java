package com.ngn.utm.manager.api.pfsenses.vpn;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ApiOpenVPNServerModel {
	private String vpnid;
	private String mode;
	private String authmode;
	private String protocol;
	private String dev_mode;
	@JsonProperty("interface") 
	private String myinterface;
	private String local_port;
	private String description;
	private String tls;
	private String tls_type;
	private String caref;
	private String certref;
	private String dh_length;
	private String ncp_enable;
	private String data_ciphers;
	private String data_ciphers_fallback;
	private String digest;
	private String engine;
	private String tunnel_network;
	private String local_network;
	private String allow_compression;
	private String duplicate_cn;
	private String dynamic_ip;
	private String topology;
	private String exit_notify;
	private String inactive_seconds;
}
