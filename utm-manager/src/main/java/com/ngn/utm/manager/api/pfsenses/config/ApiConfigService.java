package com.ngn.utm.manager.api.pfsenses.config;

import java.util.List;

import com.ngn.utm.manager.api.ApiResultResponse;

public interface ApiConfigService {
	ApiResultResponse<List<ApiConfigModel>> getConfig(int id);
	ApiResultResponse<ApiConfigModel> exportConfig(String id);
}
