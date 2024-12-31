package com.ngn.utm.manager.api.real_tech.authen;

import org.springframework.core.ParameterizedTypeReference;

import com.ngn.utm.manager.api.ApiResultResponse;
import com.ngn.utm.manager.service.CoreExchangeService;
import com.ngn.utm.manager.utils.PropUtils;

public class ApiAuthenUtil implements ApiAuthenService{
	private String baseUrlReal = PropUtils.getCoreRealTechUrl();
	private static String baseUrlReal2 = PropUtils.getCoreRealTechUrl();

	@Override
	public ApiResultResponse<ApiUserRealTechModel> login(ApiUserRealTechModel apiUserRealTechModel) {
		CoreExchangeService coreExchangeService = new CoreExchangeService(baseUrlReal);
		return coreExchangeService.post(baseUrlReal, new ParameterizedTypeReference<ApiResultResponse<ApiUserRealTechModel>>() {}, apiUserRealTechModel);
	}
	
	public static ApiResultResponse<ApiUserRealTechModel> loginStatic(ApiUserRealTechModel apiUserRealTechModel) {
		CoreExchangeService coreExchangeService = new CoreExchangeService(baseUrlReal2);
		return coreExchangeService.post(baseUrlReal2, new ParameterizedTypeReference<ApiResultResponse<ApiUserRealTechModel>>() {}, apiUserRealTechModel);
	}
}
