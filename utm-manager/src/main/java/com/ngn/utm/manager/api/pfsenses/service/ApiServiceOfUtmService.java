package com.ngn.utm.manager.api.pfsenses.service;

import java.util.List;

import com.ngn.utm.manager.api.pfsenses.ApiResultPfsenseResponse;

public interface ApiServiceOfUtmService {
	ApiResultPfsenseResponse<List<ApiServiceOfUtmModel>> readAllServiceStatues() throws Exception;
	ApiResultPfsenseResponse<Object> restartService(ApiServiceOfUtmModel apiServiceOfUtmModel) throws Exception;
	ApiResultPfsenseResponse<Object> restartAllService() throws Exception;
	ApiResultPfsenseResponse<Object> stopService(ApiServiceOfUtmModel apiServiceOfUtmModel) throws Exception;
}
