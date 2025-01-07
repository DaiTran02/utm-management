package com.ngn.utm.manager.api.real_tech.authen;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import com.ngn.utm.manager.api.ApiResultResponse;
import com.ngn.utm.manager.service.CoreExchangeService;
import com.ngn.utm.manager.utils.PropUtils;

@Component
public class ApiAuthenUtil implements ApiAuthenService{
	private final String baseUrlReal;
	
	public ApiAuthenUtil(PropUtils propUtils) {
		this.baseUrlReal = propUtils.getCoreRealTechUrl();
	}

	@Override
	public ApiResultResponse<ApiUserRealTechModel> login(ApiInputAuthenModel inputUser) {
		CoreExchangeService coreExchangeService = new CoreExchangeService(baseUrlReal);
		return coreExchangeService.authen("/api/auth/login", new ParameterizedTypeReference<ApiResultResponse<ApiUserRealTechModel>>() {}, inputUser);
	}
}
