package com.ngn.utm.manager.api.pfsenses.openvpn;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import com.ngn.utm.manager.api.pfsenses.ApiAuthenticationPfsenseModel;
import com.ngn.utm.manager.api.pfsenses.ApiResultPfsenseResponse;
import com.ngn.utm.manager.service.CoreExchangePfsenseService;
import com.ngn.utm.manager.utils.SessionUtil;

@Component
public class ApiOpenVpnUtil implements ApiOpenVpnService{
	private String url = "https://";

	@Override
	public ApiResultPfsenseResponse<ApiReadOpenVpnStatusModel> readOpenVpnStatus() throws Exception{
		String uri = SessionUtil.getDeviceInfo() == null ? "" : url+ SessionUtil.getDeviceInfo().getIpAddress();
		CoreExchangePfsenseService coreExchangePfsenseService = new CoreExchangePfsenseService();
		return coreExchangePfsenseService.get(uri+"/api/v1/status/openvpn", new ApiAuthenticationPfsenseModel(SessionUtil.getDeviceInfo()), new ParameterizedTypeReference<>() {});
	}

}
