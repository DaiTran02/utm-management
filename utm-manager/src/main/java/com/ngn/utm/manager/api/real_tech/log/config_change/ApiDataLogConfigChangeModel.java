package com.ngn.utm.manager.api.real_tech.log.config_change;

import java.util.List;

import lombok.Data;

@Data
public class ApiDataLogConfigChangeModel {
	private long count;
	private List<ApiLogConfigChangeModel> list;
}
