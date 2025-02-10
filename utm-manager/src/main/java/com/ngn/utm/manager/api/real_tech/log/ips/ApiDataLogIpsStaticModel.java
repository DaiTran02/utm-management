package com.ngn.utm.manager.api.real_tech.log.ips;

import java.util.List;

import lombok.Data;

@Data
public class ApiDataLogIpsStaticModel {
	private long count;
	private List<ApiLogIpsStaticModel> list;
}
