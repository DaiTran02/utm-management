package com.ngn.utm.manager.api.pfsenses.utm_interface;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import com.ngn.utm.manager.api.pfsenses.ApiAuthenticationPfsenseModel;
import com.ngn.utm.manager.api.pfsenses.ApiResultPfsenseResponse;
import com.ngn.utm.manager.service.CoreExchangePfsenseService;
import com.ngn.utm.manager.utils.PropUtils;
import com.ngn.utm.manager.utils.SessionUtil;

@Component
public class ApiInterfaceUtil implements ApiInterfaceService{
	private String url = "https://";
	
	private PropUtils propUtils;
	
	public ApiInterfaceUtil(PropUtils propUtils) {
		this.propUtils = propUtils;
	}
	
	private String getURL() {
		String uri = SessionUtil.getDeviceInfo() == null ? "" : url+ SessionUtil.getDeviceInfo().getIpAddress()+":"+SessionUtil.getDeviceInfo().getMntPort();
		if(propUtils.getVersionUtm().equals("v1")) {
			return uri+"/api/v1/status/interface";
		}else {
			return uri+"/api/v2/status/interface";
		}
	}
	
	@Override
	public ApiResultPfsenseResponse<List<ApiStatusInterfaceModel>> readInterfaceStatus() throws Exception {
		CoreExchangePfsenseService coreExchangePfsenseService = new CoreExchangePfsenseService(propUtils);
		return coreExchangePfsenseService.get(getURL(), new ApiAuthenticationPfsenseModel(SessionUtil.getDeviceInfo()), new ParameterizedTypeReference<>() {});
	}

}
