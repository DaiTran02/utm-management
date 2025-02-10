package com.ngn.utm.manager.api.real_tech.log.config_change;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import com.ngn.utm.manager.api.ApiConvertUtil;
import com.ngn.utm.manager.api.ApiResultResponse;
import com.ngn.utm.manager.service.CoreExchangeService;
import com.ngn.utm.manager.utils.PropUtils;

@Component
public class ApiLogConfigChangeUtil implements ApiLogConfigChangeService{

	private String baseUrlReal;
	
	public ApiLogConfigChangeUtil(PropUtils propUtils) {
		this.baseUrlReal = propUtils.getCoreRealTechUrl();
	}
	
	@Override
	public ApiResultResponse<ApiDataLogConfigChangeModel> getLogConfigChangeByFilter(
			ApiFilterLogConfigChangeModel apiFilterLogConfigChangeModel) {
		CoreExchangeService coreExchangeService = new CoreExchangeService(baseUrlReal);
		String param = ApiConvertUtil.convertToParams(apiFilterLogConfigChangeModel);
		return coreExchangeService.get("/api/private/u/logs/configchange?"+param, new ParameterizedTypeReference<>() {});
	}

	@Override
	public ApiResultResponse<ApiDataLogConfigChangeModel> getLogConfigChangeById(String id) {
		CoreExchangeService coreExchangeService = new CoreExchangeService(baseUrlReal);
		return coreExchangeService.get("/api/private/u/logs/configchange/"+id, new ParameterizedTypeReference<>() {});
	}

	@Override
	public ApiResultResponse<ApiExportLogConfigChangeModel> exportLogConfigChange(
			ApiFilterLogConfigChangeModel apiFilterLogConfigChangeModel) {
		CoreExchangeService coreExchangeService = new CoreExchangeService(baseUrlReal);
		String param = ApiConvertUtil.convertToParams(apiFilterLogConfigChangeModel);
		return coreExchangeService.post("/api/private/u/logs/configchange?"+param, new ParameterizedTypeReference<>() {},null);
	}
}
