package com.ngn.utm.manager.api.pfsenses.vpn;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import com.ngn.utm.manager.api.pfsenses.ApiAuthenticationPfsenseModel;
import com.ngn.utm.manager.api.pfsenses.ApiResultPfsenseResponse;
import com.ngn.utm.manager.service.CoreExchangePfsenseService;
import com.ngn.utm.manager.utils.PropUtils;
import com.ngn.utm.manager.utils.SessionUtil;

@Component
public class ApiVPNUtil implements ApiVPNService{
	private String url = "https://";
	
	private PropUtils propUtils;
	
	public ApiVPNUtil(PropUtils propUtils) {
		this.propUtils = propUtils;
	}
	
	private String getURL() {
		String uri = SessionUtil.getDeviceInfo() == null ? "" : url+ SessionUtil.getDeviceInfo().getIpAddress()+":"+SessionUtil.getDeviceInfo().getMntPort();
		if(propUtils.getVersionUtm().equals("v1")) {
			return uri+"/api/v1/";
		}else {
			return uri+"/api/v2/";
		}
	}

	@Override
	public ApiResultPfsenseResponse<List<ApiOpenVPNServerModel>> readOpenVPNServer() throws Exception {
		CoreExchangePfsenseService coreExchangePfsenseService = new CoreExchangePfsenseService(propUtils);
		return coreExchangePfsenseService.get(getURL()+"services/openvpn/server", new ApiAuthenticationPfsenseModel(SessionUtil.getDeviceInfo()), new ParameterizedTypeReference<>() {});
	}

	@Override
	public ApiResultPfsenseResponse<ApiReadOpenVpnStatusModel> readOpenVPNStatus() throws Exception {
		CoreExchangePfsenseService coreExchangePfsenseService = new CoreExchangePfsenseService(propUtils);
		return coreExchangePfsenseService.get(getURL()+"status/openvpn", new ApiAuthenticationPfsenseModel(SessionUtil.getDeviceInfo()), new ParameterizedTypeReference<>() {});
	}

}
