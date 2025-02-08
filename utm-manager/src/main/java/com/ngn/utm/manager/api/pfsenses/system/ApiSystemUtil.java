package com.ngn.utm.manager.api.pfsenses.system;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import com.ngn.utm.manager.api.pfsenses.ApiAuthenticationPfsenseModel;
import com.ngn.utm.manager.api.pfsenses.ApiResultPfsenseResponse;
import com.ngn.utm.manager.service.CoreExchangePfsenseService;
import com.ngn.utm.manager.utils.SessionUtil;

@Component
public class ApiSystemUtil implements ApiSystemService{
	private String url = "https://";

	@Override
	public ApiResultPfsenseResponse<ApiReadSystemStatusModel> readSystemStatus() throws Exception {
		String uri = SessionUtil.getDeviceInfo() == null ? "" : url+ SessionUtil.getDeviceInfo().getIpAddress();
		CoreExchangePfsenseService coreExchangePfsenseService = new CoreExchangePfsenseService();
		return coreExchangePfsenseService.get(uri+"/api/v1/status/system", new ApiAuthenticationPfsenseModel(SessionUtil.getDeviceInfo()), new ParameterizedTypeReference<>() {});
	}

	@Override
	public ApiResultPfsenseResponse<ApiReadSystemHostNameModel> readSystemHost() throws Exception {
		String uri = SessionUtil.getDeviceInfo() == null ? "" : url+ SessionUtil.getDeviceInfo().getIpAddress();
		CoreExchangePfsenseService coreExchangePfsenseService = new CoreExchangePfsenseService();
		return coreExchangePfsenseService.get(uri+"/api/v1/system/hostname", new ApiAuthenticationPfsenseModel(SessionUtil.getDeviceInfo()), new ParameterizedTypeReference<>() {});
	}

	@Override
	public ApiResultPfsenseResponse<ApiReadSystemVersionModel> readSystemVersion() throws Exception {
		String uri = SessionUtil.getDeviceInfo() == null ? "" : url+ SessionUtil.getDeviceInfo().getIpAddress();
		CoreExchangePfsenseService coreExchangePfsenseService = new CoreExchangePfsenseService();
		return coreExchangePfsenseService.get(uri+"/api/v1/system/version", new ApiAuthenticationPfsenseModel(SessionUtil.getDeviceInfo()), new ParameterizedTypeReference<>() {});
	}

}
