package com.ngn.utm.manager.api.pfsenses.alias;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import com.ngn.utm.manager.api.pfsenses.ApiAuthenticationPfsenseModel;
import com.ngn.utm.manager.api.pfsenses.ApiResultPfsenseResponse;
import com.ngn.utm.manager.service.CoreExchangePfsenseService;
import com.ngn.utm.manager.utils.SessionUtil;

@Component
public class ApiAliasUtil implements ApiAliasService{
	private String url = "https://";


	@Override
	public ApiResultPfsenseResponse<List<ApiReadFirewallAliasModel>> readFirewallAliases() throws Exception {
		String uri = SessionUtil.getDeviceInfo() == null ? "" : url+ SessionUtil.getDeviceInfo().getIpAddress();
		CoreExchangePfsenseService coreExchangePfsenseService = new CoreExchangePfsenseService();
		return coreExchangePfsenseService.get(uri+"/api/v1/firewall/alias", new ApiAuthenticationPfsenseModel(SessionUtil.getDeviceInfo()), new ParameterizedTypeReference<>() {});
	}

	@Override
	public ApiResultPfsenseResponse<Object> deleteFirewallAlias(String idAlies) throws Exception {
		String uri = SessionUtil.getDeviceInfo() == null ? "" : url+ SessionUtil.getDeviceInfo().getIpAddress();
		CoreExchangePfsenseService coreExchangePfsenseService = new CoreExchangePfsenseService();
		return coreExchangePfsenseService.delete(uri+"/api/v1/firewall/alias?id="+idAlies+"&apply=true", new ApiAuthenticationPfsenseModel(SessionUtil.getDeviceInfo()), new ParameterizedTypeReference<>() {});
	}

	@Override
	public ApiResultPfsenseResponse<Object> createFirewallAlias(
			ApiCreateAndUpdateAliasModel apiCreateAndUpdateAliasModel) throws Exception {
		String uri = SessionUtil.getDeviceInfo() == null ? "" : url+ SessionUtil.getDeviceInfo().getIpAddress();
		CoreExchangePfsenseService coreExchangePfsenseService = new CoreExchangePfsenseService();
		return coreExchangePfsenseService.postUseFormData(uri+"/api/v1/firewall/alias", 
				new ApiAuthenticationPfsenseModel(SessionUtil.getDeviceInfo()), apiCreateAndUpdateAliasModel, new ParameterizedTypeReference<>() {});
	}

	@Override
	public ApiResultPfsenseResponse<Object> updateFirewallAlias(
			ApiCreateAndUpdateAliasModel apiCreateAndUpdateAliasModel) throws Exception {
		String uri = SessionUtil.getDeviceInfo() == null ? "" : url+ SessionUtil.getDeviceInfo().getIpAddress();
		CoreExchangePfsenseService coreExchangePfsenseService = new CoreExchangePfsenseService();

		return coreExchangePfsenseService.putUseFormData(uri+"/api/v1/firewall/alias", 
				new ApiAuthenticationPfsenseModel(SessionUtil.getDeviceInfo()), apiCreateAndUpdateAliasModel, new ParameterizedTypeReference<>() {});
	}

}
