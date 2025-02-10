package com.ngn.utm.manager.api.real_tech.log.login_web;

import com.ngn.utm.manager.api.ApiResultResponse;

public interface ApiLoginWebService {
	ApiResultResponse<ApiDataLoginWebModel> getLogLoginWebByFilter(ApiFilterLoginWebModel apiFilterLoginWebModel);
	ApiResultResponse<ApiLoginWebModel> getLoginWebById(String id);
	ApiResultResponse<ApiExportLoginWebModel> exportLoginWebByFilter(ApiFilterLoginWebModel apiFilterLoginWebModel);
}
