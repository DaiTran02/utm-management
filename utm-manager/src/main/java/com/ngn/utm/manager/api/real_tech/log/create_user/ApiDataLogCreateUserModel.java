package com.ngn.utm.manager.api.real_tech.log.create_user;

import java.util.List;

import lombok.Data;

@Data
public class ApiDataLogCreateUserModel {
	private long count;
	private List<ApiLogCreateUserModel> list;
}
