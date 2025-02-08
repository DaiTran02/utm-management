package com.ngn.utm.manager.api.pfsenses.rule;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import com.ngn.utm.manager.api.pfsenses.ApiAuthenticationPfsenseModel;
import com.ngn.utm.manager.api.pfsenses.ApiResultPfsenseResponse;
import com.ngn.utm.manager.service.CoreExchangePfsenseService;
import com.ngn.utm.manager.utils.SessionUtil;
import com.nimbusds.jose.shaded.gson.JsonObject;

@Component
public class ApiFirewallRuleUtil implements ApiFirewallRuleService{
	private String url = "https://";

	@Override
	public ApiResultPfsenseResponse<List<JsonObject>> readExistingFirewallRules() throws Exception {
		String uri = SessionUtil.getDeviceInfo() == null ? "" : url+ SessionUtil.getDeviceInfo().getIpAddress();
		CoreExchangePfsenseService coreExchangePfsenseService = new CoreExchangePfsenseService();
		return coreExchangePfsenseService.get(uri+"/api/v1/firewall/rule", new ApiAuthenticationPfsenseModel(SessionUtil.getDeviceInfo()), new ParameterizedTypeReference<>() {});
	}

	@Override
	public ApiResultPfsenseResponse<Object> createFirewallRule(ApiFirewallRuleInputModel apiFirewallRuleInputModel)
			throws Exception {
		String uri = SessionUtil.getDeviceInfo() == null ? "" : url+ SessionUtil.getDeviceInfo().getIpAddress();
		CoreExchangePfsenseService coreExchangePfsenseService = new CoreExchangePfsenseService();
		return coreExchangePfsenseService.postUseFormData(uri+"/api/v1/firewall/rule", new ApiAuthenticationPfsenseModel(SessionUtil.getDeviceInfo()),apiFirewallRuleInputModel, new ParameterizedTypeReference<>() {});
	}

	@Override
	public ApiResultPfsenseResponse<Object> updateFirewallRule(ApiFirewallRuleInputModel apiFirewallRuleInputModel)
			throws Exception {
		String uri = SessionUtil.getDeviceInfo() == null ? "" : url+ SessionUtil.getDeviceInfo().getIpAddress();
		CoreExchangePfsenseService coreExchangePfsenseService = new CoreExchangePfsenseService();
		return coreExchangePfsenseService.put(uri+"/api/v1/firewall/rule", new ApiAuthenticationPfsenseModel(SessionUtil.getDeviceInfo()), apiFirewallRuleInputModel, new ParameterizedTypeReference<>() {});
	}

	@Override
	public ApiResultPfsenseResponse<Object> deleteFirewallRule(Integer tracker) throws Exception {
		String uri = SessionUtil.getDeviceInfo() == null ? "" : url+ SessionUtil.getDeviceInfo().getIpAddress();
		CoreExchangePfsenseService coreExchangePfsenseService = new CoreExchangePfsenseService();
		return coreExchangePfsenseService.delete(uri+"/api/v1/firewall/rule?tracker="+tracker+"&apply=true", new ApiAuthenticationPfsenseModel(SessionUtil.getDeviceInfo()), new ParameterizedTypeReference<>() {});
	}

}
