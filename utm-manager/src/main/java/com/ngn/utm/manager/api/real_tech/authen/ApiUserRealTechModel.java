package com.ngn.utm.manager.api.real_tech.authen;

import lombok.Data;

@Data
public class ApiUserRealTechModel {
	private int id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private int role;
    private String token;
}
