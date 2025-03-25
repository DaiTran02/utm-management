package com.ngn.utm.manager.api.pfsenses.rule;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import com.ngn.utm.manager.api.pfsenses.ApiAuthenticationPfsenseModel;
import com.ngn.utm.manager.api.pfsenses.ApiResultPfsenseResponse;
import com.ngn.utm.manager.service.CoreExchangePfsenseService;
import com.ngn.utm.manager.utils.PropUtils;
import com.ngn.utm.manager.utils.SessionUtil;
import com.nimbusds.jose.shaded.gson.JsonObject;

@Component
public class ApiFirewallRuleUtil implements ApiFirewallRuleService{
	private String url = "https://";
	
	private PropUtils propUtils;
	
	public ApiFirewallRuleUtil(PropUtils propUtils) {
		this.propUtils = propUtils;
	}
	
	private String getURL() {
		String uri = SessionUtil.getDeviceInfo() == null ? "" : url+ SessionUtil.getDeviceInfo().getIpAddress()+":"+SessionUtil.getDeviceInfo().getMntPort();
		if(propUtils.getVersionUtm().equals("v1")) {
			return uri+"/api/v1/firewall/rule";
		}else {
			return uri+"/api/v2/firewall/rule";
		}
	}

	@Override
	public ApiResultPfsenseResponse<List<JsonObject>> readExistingFirewallRules() throws Exception {
		CoreExchangePfsenseService coreExchangePfsenseService = new CoreExchangePfsenseService(propUtils);
		return coreExchangePfsenseService.get(getURL(), new ApiAuthenticationPfsenseModel(SessionUtil.getDeviceInfo()), new ParameterizedTypeReference<>() {});
	}

	@Override
	public ApiResultPfsenseResponse<Object> createFirewallRule(ApiFirewallRuleInputModel apiFirewallRuleInputModel)
			throws Exception {
		CoreExchangePfsenseService coreExchangePfsenseService = new CoreExchangePfsenseService(propUtils);
		return coreExchangePfsenseService.postUseFormData(getURL(), new ApiAuthenticationPfsenseModel(SessionUtil.getDeviceInfo()),apiFirewallRuleInputModel, new ParameterizedTypeReference<>() {});
	}

	@Override
	public ApiResultPfsenseResponse<Object> updateFirewallRule(ApiFirewallRuleInputModel apiFirewallRuleInputModel)
			throws Exception {
		CoreExchangePfsenseService coreExchangePfsenseService = new CoreExchangePfsenseService(propUtils);
		return coreExchangePfsenseService.put(getURL(), new ApiAuthenticationPfsenseModel(SessionUtil.getDeviceInfo()), apiFirewallRuleInputModel, new ParameterizedTypeReference<>() {});
	}

	@Override
	public ApiResultPfsenseResponse<Object> deleteFirewallRule(Integer tracker) throws Exception {
		CoreExchangePfsenseService coreExchangePfsenseService = new CoreExchangePfsenseService(propUtils);
		return coreExchangePfsenseService.delete(getURL()+"?tracker="+tracker+"&apply=true", new ApiAuthenticationPfsenseModel(SessionUtil.getDeviceInfo()), new ParameterizedTypeReference<>() {});
	}

}
