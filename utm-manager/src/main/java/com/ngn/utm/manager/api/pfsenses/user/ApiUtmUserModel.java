package com.ngn.utm.manager.api.pfsenses.user;

import lombok.Data;

@Data
public class ApiUtmUserModel {
	private String scope;
	private String descr;
	private String name;
	private String expires;
    private String dashboardcolumns;
    private String authorizedkeys;
    private String ipsecpsk;
    private String webguicss;
    private String uid;
    private String groupname;
    private Boolean disabled;
    private String password;
    private String username;
}
