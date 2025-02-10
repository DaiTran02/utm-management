package com.ngn.utm.manager.api.real_tech.log.config_change;

import com.ngn.utm.manager.api.ApiResultResponse;

public interface ApiLogConfigChangeService {
	ApiResultResponse<ApiDataLogConfigChangeModel> getLogConfigChangeByFilter(ApiFilterLogConfigChangeModel apiFilterLogConfigChangeModel);
	ApiResultResponse<ApiDataLogConfigChangeModel> getLogConfigChangeById(String id);
	ApiResultResponse<ApiExportLogConfigChangeModel> exportLogConfigChange(ApiFilterLogConfigChangeModel apiFilterLogConfigChangeModel);
}
