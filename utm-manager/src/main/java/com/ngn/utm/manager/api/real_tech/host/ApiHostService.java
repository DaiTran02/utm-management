package com.ngn.utm.manager.api.real_tech.host;

import java.util.List;

import com.ngn.utm.manager.api.ApiResultResponse;

public interface ApiHostService {
	ApiResultResponse<List<ApiHostModel>> getAllHost();
	ApiResultResponse<ApiHostModel> getOneHost(String idHost);
	ApiResultResponse<Object> createHost(ApiHostModel apiHostModel);
	ApiResultResponse<Object> updateHost(ApiHostModel apiHostModel);
	ApiResultResponse<Object> deletedHost(String idHost);
	ApiResultResponse<List<ApiConfigModel>> getConfigs();
	ApiResultResponse<ApiConfigDataModel> getFileConfig(String idConfig);
}
