package com.ngn.utm.manager.api.real_tech.log.create_user;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import com.ngn.utm.manager.api.ApiConvertUtil;
import com.ngn.utm.manager.api.ApiResultResponse;
import com.ngn.utm.manager.service.CoreExchangeService;
import com.ngn.utm.manager.utils.PropUtils;

@Component
public class ApiCreateUserUtil implements ApiCreateUserService{
	private String baseUrlReal;
	
	public ApiCreateUserUtil(PropUtils propUtils) {
		this.baseUrlReal = propUtils.getCoreRealTechUrl();
	}
	@Override
	public ApiResultResponse<ApiDataLogCreateUserModel> getDataLogCreateUserByFilter(
			ApiFilterLogCreateUserModel apiFilterLogCreateUserModel) {
		CoreExchangeService coreExchangeService = new CoreExchangeService(baseUrlReal);
		String param = ApiConvertUtil.convertToParams(apiFilterLogCreateUserModel);
		return coreExchangeService.get("/api/private/u/logs/createuser?"+param, new ParameterizedTypeReference<>() {});
	}

	@Override
	public ApiResultResponse<ApiLogCreateUserModel> getDataLogCreateUserById(String id) {
		CoreExchangeService coreExchangeService = new CoreExchangeService(baseUrlReal);
		return coreExchangeService.get("/api/private/u/logs/createuser/"+id, new ParameterizedTypeReference<>() {});
	}

	@Override
	public ApiResultResponse<ApiExportLogCreateUserModel> exportLogCreateUserByFilter(
			ApiFilterLogCreateUserModel apiFilterLogCreateUserModel) {
		CoreExchangeService coreExchangeService = new CoreExchangeService(baseUrlReal);
		String param = ApiConvertUtil.convertToParams(apiFilterLogCreateUserModel);
		return coreExchangeService.post("/api/private/u/logs/createuser?"+param, new ParameterizedTypeReference<>() {},null);
	}

}
