package com.ngn.utm.manager.api.real_tech.log.connectivity;

import java.util.List;

import lombok.Data;

@Data
public class ApiDataLogConnectiviStaticModel {
	private long count;
	private List<ApiLogConnectivityStaticModel> list;
}
