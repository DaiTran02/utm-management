package com.ngn.utm.manager.api.pfsenses.logs;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import com.ngn.utm.manager.api.pfsenses.ApiAuthenticationPfsenseModel;
import com.ngn.utm.manager.api.pfsenses.ApiResultPfsenseResponse;
import com.ngn.utm.manager.service.CoreExchangePfsenseService;
import com.ngn.utm.manager.utils.SessionUtil;

@Component
public class ApiLogUtils implements ApiLogService{
	
	private String url = "https://";

	@Override
	public ApiResultPfsenseResponse<List<String>> readSystemLog() throws Exception {
		String uri = SessionUtil.getDeviceInfo() == null ? "" : url+ SessionUtil.getDeviceInfo().getIpAddress();
		CoreExchangePfsenseService coreExchangePfsenseService = new CoreExchangePfsenseService();
		return coreExchangePfsenseService.get(uri+"/api/v1/status/log/system", new ApiAuthenticationPfsenseModel(SessionUtil.getDeviceInfo()), new ParameterizedTypeReference<>() {});
	}

	@Override
	public ApiResultPfsenseResponse<List<String>> readFirewallLog() throws Exception {
		String uri = SessionUtil.getDeviceInfo() == null ? "" : url+ SessionUtil.getDeviceInfo().getIpAddress();
		CoreExchangePfsenseService coreExchangePfsenseService = new CoreExchangePfsenseService();
		return coreExchangePfsenseService.get(uri+"/api/v1/status/log/firewall", new ApiAuthenticationPfsenseModel(SessionUtil.getDeviceInfo()), new ParameterizedTypeReference<>() {});
	}

	@Override
	public ApiResultPfsenseResponse<List<String>> readDHCPLog() throws Exception {
		String uri = SessionUtil.getDeviceInfo() == null ? "" : url+ SessionUtil.getDeviceInfo().getIpAddress();
		CoreExchangePfsenseService coreExchangePfsenseService = new CoreExchangePfsenseService();
		return coreExchangePfsenseService.get(uri+"/api/v1/status/log/dhcp", new ApiAuthenticationPfsenseModel(SessionUtil.getDeviceInfo()), new ParameterizedTypeReference<>() {});
	}

}
