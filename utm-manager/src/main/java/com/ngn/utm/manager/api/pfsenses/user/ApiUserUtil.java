package com.ngn.utm.manager.api.pfsenses.user;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import com.ngn.utm.manager.api.pfsenses.ApiAuthenticationPfsenseModel;
import com.ngn.utm.manager.api.pfsenses.ApiResultPfsenseResponse;
import com.ngn.utm.manager.service.CoreExchangePfsenseService;
import com.ngn.utm.manager.utils.SessionUtil;

@Component
public class ApiUserUtil implements ApiUserSerivce{
	private String url = "https://";

	@Override
	public ApiResultPfsenseResponse<List<ApiUtmUserModel>> readUsers() throws Exception {
		String uri = SessionUtil.getDeviceInfo() == null ? "" : url+ SessionUtil.getDeviceInfo().getIpAddress();
		CoreExchangePfsenseService coreExchangePfsenseService = new CoreExchangePfsenseService();
		return coreExchangePfsenseService.get(uri+"/api/v1/user", new ApiAuthenticationPfsenseModel(SessionUtil.getDeviceInfo()), new ParameterizedTypeReference<>() {});
	}

	@Override
	public ApiResultPfsenseResponse<Object> createUser(ApiUtmUserModel apiUtmUserModel) throws Exception {
		String uri = SessionUtil.getDeviceInfo() == null ? "" : url+ SessionUtil.getDeviceInfo().getIpAddress();
		CoreExchangePfsenseService coreExchangePfsenseService = new CoreExchangePfsenseService();
		return coreExchangePfsenseService.get(uri+"/api/v1/user", new ApiAuthenticationPfsenseModel(SessionUtil.getDeviceInfo()), new ParameterizedTypeReference<>() {});
	}

	@Override
	public ApiResultPfsenseResponse<Object> updateUser(ApiUtmUserModel apiUtmUserModel) throws Exception {
		String uri = SessionUtil.getDeviceInfo() == null ? "" : url+ SessionUtil.getDeviceInfo().getIpAddress();
		CoreExchangePfsenseService coreExchangePfsenseService = new CoreExchangePfsenseService();
		return coreExchangePfsenseService.get(uri+"/api/v1/user", new ApiAuthenticationPfsenseModel(SessionUtil.getDeviceInfo()), new ParameterizedTypeReference<>() {});
	}

	@Override
	public ApiResultPfsenseResponse<Object> deleteUser(String idUser) throws Exception {
		String uri = SessionUtil.getDeviceInfo() == null ? "" : url+ SessionUtil.getDeviceInfo().getIpAddress();
		CoreExchangePfsenseService coreExchangePfsenseService = new CoreExchangePfsenseService();
		return coreExchangePfsenseService.get(uri+"/api/v1/user?username="+idUser, new ApiAuthenticationPfsenseModel(SessionUtil.getDeviceInfo()), new ParameterizedTypeReference<>() {});
	}

}
