package com.ngn.utm.manager.api.real_tech.log.create_user;

import com.ngn.utm.manager.api.ApiResultResponse;

public interface ApiCreateUserService {
	ApiResultResponse<ApiDataLogCreateUserModel> getDataLogCreateUserByFilter(ApiFilterLogCreateUserModel apiFilterLogCreateUserModel);
	ApiResultResponse<ApiLogCreateUserModel> getDataLogCreateUserById(String id);
	ApiResultResponse<ApiExportLogCreateUserModel> exportLogCreateUserByFilter(ApiFilterLogCreateUserModel apiFilterLogCreateUserModel);
}
