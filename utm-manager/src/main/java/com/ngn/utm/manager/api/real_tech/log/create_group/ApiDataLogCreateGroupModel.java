package com.ngn.utm.manager.api.real_tech.log.create_group;

import java.util.List;

import lombok.Data;

@Data
public class ApiDataLogCreateGroupModel {
	private long count;
	private List<ApiLogCreateGroupModel> list;
}
