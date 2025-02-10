package com.ngn.utm.manager.api.real_tech.log.create_group;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import com.ngn.utm.manager.api.ApiConvertUtil;
import com.ngn.utm.manager.api.ApiResultResponse;
import com.ngn.utm.manager.service.CoreExchangeService;
import com.ngn.utm.manager.utils.PropUtils;

@Component
public class ApiLogCreateGroupUtil implements ApiLogCreateGroupService{
	private String baseUrlReal;
	
	public ApiLogCreateGroupUtil(PropUtils propUtils) {
		this.baseUrlReal = propUtils.getCoreRealTechUrl();
	}
	@Override
	public ApiResultResponse<ApiDataLogCreateGroupModel> getLogCreateGroupByFilter(
			ApiLogCreateGroupFilterModel apiLogCreateGroupFilterModel) {
		CoreExchangeService coreExchangeService = new CoreExchangeService(baseUrlReal);
		String param = ApiConvertUtil.convertToParams(apiLogCreateGroupFilterModel);
		return coreExchangeService.get("/api/private/u/logs/creategroup?"+param, new ParameterizedTypeReference<>() {});
	}

	@Override
	public ApiResultResponse<ApiLogCreateGroupModel> getLogCreateGroupById(String id) {
		CoreExchangeService coreExchangeService = new CoreExchangeService(baseUrlReal);
		return coreExchangeService.get("/api/private/u/logs/creategroup/"+id, new ParameterizedTypeReference<>() {});
	}

	@Override
	public ApiResultResponse<ApiExportLogCreateGroupModel> exportLogCreateGroupByFilter(
			ApiLogCreateGroupFilterModel apiLogCreateGroupFilterModel) {
		CoreExchangeService coreExchangeService = new CoreExchangeService(baseUrlReal);
		String param = ApiConvertUtil.convertToParams(apiLogCreateGroupFilterModel);
		return coreExchangeService.post("/api/private/u/logs/creategroup?"+param, new ParameterizedTypeReference<>() {},null);
	}
	
}
