package com.ngn.utm.manager.api.pfsenses.service;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import com.ngn.utm.manager.api.pfsenses.ApiAuthenticationPfsenseModel;
import com.ngn.utm.manager.api.pfsenses.ApiResultPfsenseResponse;
import com.ngn.utm.manager.service.CoreExchangePfsenseService;
import com.ngn.utm.manager.utils.PropUtils;
import com.ngn.utm.manager.utils.SessionUtil;

@Component
public class ApiServiceOfUtmUtil implements ApiServiceOfUtmService{
	private String url = "https://";
	
	private PropUtils propUtils;
	
	public ApiServiceOfUtmUtil(PropUtils propUtils) {
		this.propUtils = propUtils;
	}
	
	private String getURL() {
		String uri = SessionUtil.getDeviceInfo() == null ? "" : url+ SessionUtil.getDeviceInfo().getIpAddress()+":"+SessionUtil.getDeviceInfo().getMntPort();
		if(propUtils.getVersionUtm().equals("v1")) {
			return uri+"/api/v1/services";
		}else {
			return uri+"/api/v2/services";
		}
	}
	
	@Override
	public ApiResultPfsenseResponse<List<ApiServiceOfUtmModel>> readAllServiceStatues() throws Exception {
		CoreExchangePfsenseService coreExchangePfsenseService = new CoreExchangePfsenseService(propUtils);
		return coreExchangePfsenseService.get(getURL(), new ApiAuthenticationPfsenseModel(SessionUtil.getDeviceInfo()), new ParameterizedTypeReference<>() {});
	}

	@Override
	public ApiResultPfsenseResponse<Object> restartService(ApiServiceOfUtmModel apiServiceOfUtmModel) throws Exception {
		CoreExchangePfsenseService coreExchangePfsenseService = new CoreExchangePfsenseService(propUtils);
		return coreExchangePfsenseService.postUseFormData(getURL()+"/restart", new ApiAuthenticationPfsenseModel(SessionUtil.getDeviceInfo()), apiServiceOfUtmModel, new ParameterizedTypeReference<>() {});
	}

	@Override
	public ApiResultPfsenseResponse<Object> restartAllService() throws Exception {
		CoreExchangePfsenseService coreExchangePfsenseService = new CoreExchangePfsenseService(propUtils);
		return coreExchangePfsenseService.postUseFormData(getURL()+"/start", new ApiAuthenticationPfsenseModel(SessionUtil.getDeviceInfo()), null, new ParameterizedTypeReference<>() {});
	}

	@Override
	public ApiResultPfsenseResponse<Object> stopService() throws Exception {
		CoreExchangePfsenseService coreExchangePfsenseService = new CoreExchangePfsenseService(propUtils);
		return coreExchangePfsenseService.postUseFormData(getURL()+"/stop", new ApiAuthenticationPfsenseModel(SessionUtil.getDeviceInfo()), null, new ParameterizedTypeReference<>() {});
	}

}
