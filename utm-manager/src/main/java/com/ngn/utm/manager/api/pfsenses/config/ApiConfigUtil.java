package com.ngn.utm.manager.api.pfsenses.config;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import com.ngn.utm.manager.api.ApiResultResponse;
import com.ngn.utm.manager.service.CoreExchangeService;
import com.ngn.utm.manager.utils.PropUtils;

@Component
public class ApiConfigUtil implements ApiConfigService{

	private String baseUrlReal;
	
	public ApiConfigUtil(PropUtils propUtils) {
		this.baseUrlReal = propUtils.getCoreRealTechUrl();
	}
	
	@Override
	public ApiResultResponse<List<ApiConfigModel>> getConfig(int id) {
		CoreExchangeService coreExchangeService = new CoreExchangeService(baseUrlReal);
		return coreExchangeService.get("/api/private/u/host/"+id+"/config", new ParameterizedTypeReference<>() {});
	}

	@Override
	public ApiResultResponse<ApiConfigModel> exportConfig(String id) {
		CoreExchangeService coreExchangeService = new CoreExchangeService(baseUrlReal);
		return coreExchangeService.post("/api/private/u/host/config/"+id, null, new ParameterizedTypeReference<>() {});
	}

}
