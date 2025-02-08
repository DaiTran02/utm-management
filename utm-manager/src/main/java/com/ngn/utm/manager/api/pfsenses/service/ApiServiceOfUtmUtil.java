package com.ngn.utm.manager.api.pfsenses.service;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import com.ngn.utm.manager.api.pfsenses.ApiAuthenticationPfsenseModel;
import com.ngn.utm.manager.api.pfsenses.ApiResultPfsenseResponse;
import com.ngn.utm.manager.service.CoreExchangePfsenseService;
import com.ngn.utm.manager.utils.SessionUtil;

@Component
public class ApiServiceOfUtmUtil implements ApiServiceOfUtmService{
	private String url = "https://";
	
	@Override
	public ApiResultPfsenseResponse<List<ApiServiceOfUtmModel>> readAllServiceStatues() throws Exception {
		String uri = SessionUtil.getDeviceInfo() == null ? "" : url+ SessionUtil.getDeviceInfo().getIpAddress();
		CoreExchangePfsenseService coreExchangePfsenseService = new CoreExchangePfsenseService();
		return coreExchangePfsenseService.get(uri+"/api/v1/services", new ApiAuthenticationPfsenseModel(SessionUtil.getDeviceInfo()), new ParameterizedTypeReference<>() {});
	}

	@Override
	public ApiResultPfsenseResponse<Object> restartService(ApiServiceOfUtmModel apiServiceOfUtmModel) throws Exception {
		String uri = SessionUtil.getDeviceInfo() == null ? "" : url+ SessionUtil.getDeviceInfo().getIpAddress();
		CoreExchangePfsenseService coreExchangePfsenseService = new CoreExchangePfsenseService();
		return coreExchangePfsenseService.postUseFormData(uri+"/api/v1/services/restart", new ApiAuthenticationPfsenseModel(SessionUtil.getDeviceInfo()), apiServiceOfUtmModel, new ParameterizedTypeReference<>() {});
	}

	@Override
	public ApiResultPfsenseResponse<Object> restartAllService() throws Exception {
		String uri = SessionUtil.getDeviceInfo() == null ? "" : url+ SessionUtil.getDeviceInfo().getIpAddress();
		CoreExchangePfsenseService coreExchangePfsenseService = new CoreExchangePfsenseService();
		return coreExchangePfsenseService.postUseFormData(uri+"/api/v1/services/start", new ApiAuthenticationPfsenseModel(SessionUtil.getDeviceInfo()), null, new ParameterizedTypeReference<>() {});
	}

	@Override
	public ApiResultPfsenseResponse<Object> stopService(ApiServiceOfUtmModel apiServiceOfUtmModel) throws Exception {
		String uri = SessionUtil.getDeviceInfo() == null ? "" : url+ SessionUtil.getDeviceInfo().getIpAddress();
		CoreExchangePfsenseService coreExchangePfsenseService = new CoreExchangePfsenseService();
		return coreExchangePfsenseService.postUseFormData(uri+"/api/v1/services/stop", new ApiAuthenticationPfsenseModel(SessionUtil.getDeviceInfo()), apiServiceOfUtmModel, new ParameterizedTypeReference<>() {});
	}

}
