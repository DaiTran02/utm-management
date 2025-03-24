package com.ngn.utm.manager.api.pfsenses.vpn;

import java.util.List;

import lombok.Data;

@Data
public class ApiReadOpenVpnStatusModel {
	private List<Server> servers;
	private List<Object> p2p_servers;
	private List<Object> clients;
	@Data
	public class Server{
		public String port;
		public String mode;
		public String name;
		public List<Object> conns;
		public String vpnid;
		public String mgmt;
	}
}
