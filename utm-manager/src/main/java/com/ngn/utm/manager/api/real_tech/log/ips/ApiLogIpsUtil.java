package com.ngn.utm.manager.api.real_tech.log.ips;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import com.ngn.utm.manager.api.ApiConvertUtil;
import com.ngn.utm.manager.api.ApiResultResponse;
import com.ngn.utm.manager.service.CoreExchangeService;
import com.ngn.utm.manager.utils.PropUtils;

@Component
public class ApiLogIpsUtil implements ApiLogIpsService{
	private String baseUrlReal;
	
	public ApiLogIpsUtil(PropUtils propUtils) {
		this.baseUrlReal = propUtils.getCoreRealTechUrl();
	}

	@Override
	public ApiResultResponse<ApiDataLogIpsStaticModel> getIpsStatictisByFilter(
			ApiFilterLogIpsModel apiFilterLogIpsModel) {
		CoreExchangeService coreExchangeService = new CoreExchangeService(baseUrlReal);
		String param = ApiConvertUtil.convertToParams(apiFilterLogIpsModel);
		return coreExchangeService.get("/api/private/u/logs/ips/statistic?"+param, new ParameterizedTypeReference<>() {});
	}

	@Override
	public ApiResultResponse<ApiExportLogIpsModel> exportIpsStatictisByFilter(
			ApiFilterLogIpsModel apiFilterLogIpsModel) {
		CoreExchangeService coreExchangeService = new CoreExchangeService(baseUrlReal);
		String param = ApiConvertUtil.convertToParams(apiFilterLogIpsModel);
		return coreExchangeService.post("/api/private/u/logs/ips/statistic?"+param, new ParameterizedTypeReference<>() {},null);
	}

	@Override
	public ApiResultResponse<ApiDataLogIpsModel> getIpsByFilter(ApiFilterLogIpsModel apiFilterLogIpsModel) {
		CoreExchangeService coreExchangeService = new CoreExchangeService(baseUrlReal);
		String param = ApiConvertUtil.convertToParams(apiFilterLogIpsModel);
		System.out.println(param);
		return coreExchangeService.get("/api/private/u/logs/ips?"+param, new ParameterizedTypeReference<>() {});
	}

	@Override
	public ApiResultResponse<ApiExportLogIpsModel> exportIpsByFilter(ApiFilterLogIpsModel apiFilterLogIpsModel) {
		CoreExchangeService coreExchangeService = new CoreExchangeService(baseUrlReal);
		String param = ApiConvertUtil.convertToParams(apiFilterLogIpsModel);
		return coreExchangeService.post("/api/private/u/logs/ips?"+param, new ParameterizedTypeReference<>() {},null);
	}

	@Override
	public ApiResultResponse<ApiLogIpsModel> getIpsById(String id) {
		CoreExchangeService coreExchangeService = new CoreExchangeService(baseUrlReal);
		return coreExchangeService.get("/api/private/u/logs/ips/", new ParameterizedTypeReference<>() {});
	}

}
