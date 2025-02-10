package com.ngn.utm.manager.api.real_tech.log.login_ssh;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import com.ngn.utm.manager.api.ApiConvertUtil;
import com.ngn.utm.manager.api.ApiResultResponse;
import com.ngn.utm.manager.service.CoreExchangeService;
import com.ngn.utm.manager.utils.PropUtils;

@Component
public class ApiLoginSSHUtil implements ApiLoginSSHSerivce{
	private String baseUrlReal;
	
	public ApiLoginSSHUtil(PropUtils propUtils) {
		this.baseUrlReal = propUtils.getCoreRealTechUrl();
	}
	@Override
	public ApiResultResponse<ApiDataLoginSSHModel> getLogLoginSSHByFilter(
			ApiFilterLoginSSHModel apiFilterLoginSSHModel) {
		CoreExchangeService coreExchangeService = new CoreExchangeService(baseUrlReal);
		String param = ApiConvertUtil.convertToParams(apiFilterLoginSSHModel);
		return coreExchangeService.get("/api/private/u/logs/loginssh?"+param, new ParameterizedTypeReference<>() {});
	}

	@Override
	public ApiResultResponse<ApiLoginSSHModel> getLoginSSHById(String id) {
		CoreExchangeService coreExchangeService = new CoreExchangeService(baseUrlReal);
		return coreExchangeService.get("/api/private/u/logs/loginssh?", new ParameterizedTypeReference<>() {});
	}

	@Override
	public ApiResultResponse<ApiExportLoginSSHModel> exportLoginSSHByFilter(
			ApiFilterLoginSSHModel apiFilterLoginSSHModel) {
		CoreExchangeService coreExchangeService = new CoreExchangeService(baseUrlReal);
		String param = ApiConvertUtil.convertToParams(apiFilterLoginSSHModel);
		return coreExchangeService.post("/api/private/u/logs/loginssh?"+param, new ParameterizedTypeReference<>() {},null);
	}

}
