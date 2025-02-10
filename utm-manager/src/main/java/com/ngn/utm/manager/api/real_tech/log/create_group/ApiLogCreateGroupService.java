package com.ngn.utm.manager.api.real_tech.log.create_group;

import com.ngn.utm.manager.api.ApiResultResponse;

public interface ApiLogCreateGroupService {
	ApiResultResponse<ApiDataLogCreateGroupModel> getLogCreateGroupByFilter(ApiLogCreateGroupFilterModel apiLogCreateGroupFilterModel);
	ApiResultResponse<ApiLogCreateGroupModel> getLogCreateGroupById(String id);
	ApiResultResponse<ApiExportLogCreateGroupModel> exportLogCreateGroupByFilter(ApiLogCreateGroupFilterModel apiLogCreateGroupFilterModel);
}
