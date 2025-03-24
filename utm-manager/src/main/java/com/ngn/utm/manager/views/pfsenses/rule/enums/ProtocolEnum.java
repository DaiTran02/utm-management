package com.ngn.utm.manager.views.pfsenses.rule.enums;

public enum ProtocolEnum {
	ANY("any", "Any"),
	TCP("tcp", "TCP"),
	UDP("udp", "UDP"),
	TCP_UDP("tcp/udp", "TCP/UDP"),
	ICMP("icmp", "ICMP"),
	ESP("esp", "ESP"),
	AH("ah", "AH"),
	GRE("gre", "GRE"),
	ETHERIP("etherip", "EoIP"),
	IPV6("ipv6", "IPV6"),
	IGMP("igmp", "IGMP"),
	PIM("pim", "PIM"),
	OSPF("ospf", "OSPF"),
	SCTP("sctp", "SCTP"),
	CARP("carp", "CARP"),
	PFSYNC("pfsync", "PFSYNC");

	private final String code;
	private final String description;

	ProtocolEnum(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
}