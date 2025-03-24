package com.ngn.utm.manager.api.pfsenses.user;

import java.util.List;

import lombok.Data;

@Data
public class ApiUtmUserInputModel {
    private String authorizedkeys;
    private List<Object> cert;
    private String descr;
    private boolean disabled;
    private String expires;
    private String ipsecpsk;
    private String password;
    private List<Object> priv;
    private String username;
}
