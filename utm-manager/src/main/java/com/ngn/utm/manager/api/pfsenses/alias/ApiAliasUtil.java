package com.ngn.utm.manager.api.pfsenses.alias;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import com.ngn.utm.manager.api.pfsenses.ApiAuthenticationPfsenseModel;
import com.ngn.utm.manager.api.pfsenses.ApiResultPfsenseResponse;
import com.ngn.utm.manager.service.CoreExchangePfsenseService;
import com.ngn.utm.manager.utils.PropUtils;
import com.ngn.utm.manager.utils.SessionUtil;

@Component
public class ApiAliasUtil implements ApiAliasService{
	private String url = "https://";

	private PropUtils propUtils;
	
	public ApiAliasUtil(PropUtils propUtils) {
		this.propUtils = propUtils;
	}
	
	private String getURL() {
		String uri = SessionUtil.getDeviceInfo() == null ? "" : url+ SessionUtil.getDeviceInfo().getIpAddress()+":"+SessionUtil.getDeviceInfo().getMntPort();
		if(propUtils.getVersionUtm().equals("v1")) {
			return uri+"/api/v1/firewall/alias";
		}else {
			return uri+"/api/v2/firewall/aliases";
		}
	}

	@Override
	public ApiResultPfsenseResponse<List<ApiReadFirewallAliasModel>> readFirewallAliases() throws Exception {
		CoreExchangePfsenseService coreExchangePfsenseService = new CoreExchangePfsenseService(propUtils);
		return coreExchangePfsenseService.get(getURL(), new ApiAuthenticationPfsenseModel(SessionUtil.getDeviceInfo()), new ParameterizedTypeReference<>() {});
	}

	@Override
	public ApiResultPfsenseResponse<Object> deleteFirewallAlias(String idAlies) throws Exception {
		CoreExchangePfsenseService coreExchangePfsenseService = new CoreExchangePfsenseService(propUtils);
		return coreExchangePfsenseService.delete(getURL()+"?id="+idAlies+"&apply=true", new ApiAuthenticationPfsenseModel(SessionUtil.getDeviceInfo()), new ParameterizedTypeReference<>() {});
	}

	@Override
	public ApiResultPfsenseResponse<Object> createFirewallAlias(
			ApiCreateAndUpdateAliasModel apiCreateAndUpdateAliasModel) throws Exception {
		CoreExchangePfsenseService coreExchangePfsenseService = new CoreExchangePfsenseService(propUtils);
		return coreExchangePfsenseService.postUseFormData(getURL(), 
				new ApiAuthenticationPfsenseModel(SessionUtil.getDeviceInfo()), apiCreateAndUpdateAliasModel, new ParameterizedTypeReference<>() {});
	}

	@Override
	public ApiResultPfsenseResponse<Object> updateFirewallAlias(
			ApiCreateAndUpdateAliasModel apiCreateAndUpdateAliasModel) throws Exception {
		CoreExchangePfsenseService coreExchangePfsenseService = new CoreExchangePfsenseService(propUtils);

		return coreExchangePfsenseService.putUseFormData(getURL(), 
				new ApiAuthenticationPfsenseModel(SessionUtil.getDeviceInfo()), apiCreateAndUpdateAliasModel, new ParameterizedTypeReference<>() {});
	}

}
