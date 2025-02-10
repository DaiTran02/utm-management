package com.ngn.utm.manager.api.real_tech.log.connectivity;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import com.ngn.utm.manager.api.ApiConvertUtil;
import com.ngn.utm.manager.api.ApiResultResponse;
import com.ngn.utm.manager.service.CoreExchangeService;
import com.ngn.utm.manager.utils.PropUtils;

@Component
public class ApiLogConnectivityUtil implements ApiLogConnectivityService{

	private String baseUrlReal;
	
	public ApiLogConnectivityUtil(PropUtils propUtils) {
		this.baseUrlReal = propUtils.getCoreRealTechUrl();
	}
	
	@Override
	public ApiResultResponse<ApiDataLogConnectivityModel> getLogConnectivityByFilter(
			ApiFilterLogConnectivityModel apiFilterLogConnectivityModel) {
		CoreExchangeService coreExchangeService = new CoreExchangeService(baseUrlReal);
		String param = ApiConvertUtil.convertToParams(apiFilterLogConnectivityModel);
		return coreExchangeService.get("/api/private/u/logs/connectivity?"+param, new ParameterizedTypeReference<>() {});
	}

	@Override
	public ApiResultResponse<ApiExportLogConnectivityModel> exportLogConnectivityByFilter(
			ApiFilterLogConnectivityModel apiFilterLogConnectivityModel) {
		CoreExchangeService coreExchangeService = new CoreExchangeService(baseUrlReal);
		String param = ApiConvertUtil.convertToParams(apiFilterLogConnectivityModel);
		return coreExchangeService.post("/api/private/u/logs/connectivity?"+param, new ParameterizedTypeReference<>() {}, null);
	} 

	@Override
	public ApiResultResponse<ApiLogConnectivityModel> getLogConnectivityById(String idLog) {
		CoreExchangeService coreExchangeService = new CoreExchangeService(baseUrlReal);
		return coreExchangeService.get("/api/private/u/logs/connectivity/"+idLog, new ParameterizedTypeReference<>() {});
	}

	@Override
	public ApiResultResponse<ApiDataLogConnectiviStaticModel> getLogConnectivityStaticByFilter(
			ApiFilterLogConnectivityModel apiFilterLogConnectivityModel) {
		CoreExchangeService coreExchangeService = new CoreExchangeService(baseUrlReal);
		String param = ApiConvertUtil.convertToParams(apiFilterLogConnectivityModel);
		return coreExchangeService.get("/api/private/u/logs/connectivity/statistic?"+param, new ParameterizedTypeReference<>() {});
	}

	@Override
	public ApiResultResponse<ApiExportLogConnectivityModel> exportLogConnectivityStaticByFilter(
			ApiFilterLogConnectivityModel apiFilterLogConnectivityModel) {
		CoreExchangeService coreExchangeService = new CoreExchangeService(baseUrlReal);
		String param = ApiConvertUtil.convertToParams(apiFilterLogConnectivityModel);
		return coreExchangeService.post("/api/private/u/logs/connectivity/statistic?"+param, new ParameterizedTypeReference<>() {},null);
	}

}
