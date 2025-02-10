package com.ngn.utm.manager.api.real_tech.log.login_web;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import com.ngn.utm.manager.api.ApiConvertUtil;
import com.ngn.utm.manager.api.ApiResultResponse;
import com.ngn.utm.manager.service.CoreExchangeService;
import com.ngn.utm.manager.utils.PropUtils;

@Component
public class ApiLoginWebUtil implements ApiLoginWebService{
	private String baseUrlReal;
	
	public ApiLoginWebUtil(PropUtils propUtils) {
		this.baseUrlReal = propUtils.getCoreRealTechUrl();
	}
	@Override
	public ApiResultResponse<ApiDataLoginWebModel> getLogLoginWebByFilter(
			ApiFilterLoginWebModel apiFilterLoginWebModel) {
		CoreExchangeService coreExchangeService = new CoreExchangeService(baseUrlReal);
		String param = ApiConvertUtil.convertToParams(apiFilterLoginWebModel);
		return coreExchangeService.get("/api/private/u/logs/loginweb?"+param, new ParameterizedTypeReference<>() {});
	}

	@Override
	public ApiResultResponse<ApiLoginWebModel> getLoginWebById(String id) {
		CoreExchangeService coreExchangeService = new CoreExchangeService(baseUrlReal);
		return coreExchangeService.get("/api/private/u/logs/loginweb/"+id, new ParameterizedTypeReference<>() {});
	}

	@Override
	public ApiResultResponse<ApiExportLoginWebModel> exportLoginWebByFilter(
			ApiFilterLoginWebModel apiFilterLoginWebModel) {
		CoreExchangeService coreExchangeService = new CoreExchangeService(baseUrlReal);
		String param = ApiConvertUtil.convertToParams(apiFilterLoginWebModel);
		return coreExchangeService.post("/api/private/u/logs/loginweb?"+param, new ParameterizedTypeReference<>() {},null);
	}

}
