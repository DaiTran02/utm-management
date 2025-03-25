package com.ngn.utm.manager.api.pfsenses.user;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import com.ngn.utm.manager.api.pfsenses.ApiAuthenticationPfsenseModel;
import com.ngn.utm.manager.api.pfsenses.ApiResultPfsenseResponse;
import com.ngn.utm.manager.service.CoreExchangePfsenseService;
import com.ngn.utm.manager.utils.PropUtils;
import com.ngn.utm.manager.utils.SessionUtil;

@Component
public class ApiUserUtil implements ApiUserSerivce{
	private String url = "https://";
	
	private PropUtils propUtils;
	
	public ApiUserUtil(PropUtils propUtils) {
		this.propUtils = propUtils;
	}
	
	private String getURL() {
		String uri = SessionUtil.getDeviceInfo() == null ? "" : url+ SessionUtil.getDeviceInfo().getIpAddress()+":"+SessionUtil.getDeviceInfo().getMntPort();
		if(propUtils.getVersionUtm().equals("v1")) {
			return uri+"/api/v1/user";
		}else {
			return uri+"/api/v2/users";
		}
	}

	@Override
	public ApiResultPfsenseResponse<List<ApiUtmUserModel>> readUsers() throws Exception {
		CoreExchangePfsenseService coreExchangePfsenseService = new CoreExchangePfsenseService(propUtils);
		System.out.println("URL ne: "+getURL());
		return coreExchangePfsenseService.get(getURL(), new ApiAuthenticationPfsenseModel(SessionUtil.getDeviceInfo()), new ParameterizedTypeReference<>() {});
	}

	@Override
	public ApiResultPfsenseResponse<Object> createUser(ApiUtmUserInputModel apiUtmUserModel) throws Exception {
		CoreExchangePfsenseService coreExchangePfsenseService = new CoreExchangePfsenseService(propUtils);
		return coreExchangePfsenseService.postUseFormData(getURL(), new ApiAuthenticationPfsenseModel(SessionUtil.getDeviceInfo()),apiUtmUserModel, new ParameterizedTypeReference<>() {});
	}

	@Override
	public ApiResultPfsenseResponse<Object> updateUser(ApiUtmUserInputModel apiUtmUserModel) throws Exception {
		CoreExchangePfsenseService coreExchangePfsenseService = new CoreExchangePfsenseService(propUtils);
		return coreExchangePfsenseService.putUseFormData(getURL(), new ApiAuthenticationPfsenseModel(SessionUtil.getDeviceInfo()),apiUtmUserModel, new ParameterizedTypeReference<>() {});
	}

	@Override
	public ApiResultPfsenseResponse<Object> deleteUser(String idUser) throws Exception {
		CoreExchangePfsenseService coreExchangePfsenseService = new CoreExchangePfsenseService(propUtils);
		return coreExchangePfsenseService.delete(getURL()+"?username="+idUser, new ApiAuthenticationPfsenseModel(SessionUtil.getDeviceInfo()), new ParameterizedTypeReference<>() {});
	}

}
