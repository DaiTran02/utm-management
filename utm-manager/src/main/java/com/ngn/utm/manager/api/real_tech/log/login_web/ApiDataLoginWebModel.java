package com.ngn.utm.manager.api.real_tech.log.login_web;

import java.util.List;

import lombok.Data;

@Data
public class ApiDataLoginWebModel {
	private long count;
	private List<ApiLoginWebModel> list;
}
