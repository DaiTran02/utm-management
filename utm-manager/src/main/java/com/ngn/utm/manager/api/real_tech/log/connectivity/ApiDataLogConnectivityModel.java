package com.ngn.utm.manager.api.real_tech.log.connectivity;

import java.util.List;

import lombok.Data;

@Data
public class ApiDataLogConnectivityModel {
	private long count;
	private List<ApiLogConnectivityModel> list;
}
