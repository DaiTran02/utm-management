package com.ngn.utm.manager.api.real_tech.host;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import com.ngn.utm.manager.api.ApiResultResponse;
import com.ngn.utm.manager.service.CoreExchangeService;
import com.ngn.utm.manager.utils.PropUtils;

@Component
public class ApiHostUtil implements ApiHostService{
	private String baseUrlReal;
	
	public ApiHostUtil(PropUtils propUtils) {
		this.baseUrlReal = propUtils.getCoreRealTechUrl();
	}

	@Override
	public ApiResultResponse<List<ApiHostModel>> getAllHost() {
		CoreExchangeService coreExchangeService = new CoreExchangeService(baseUrlReal);
		return coreExchangeService.get("/api/private/u/host", new ParameterizedTypeReference<ApiResultResponse<List<ApiHostModel>>>() {});
	}

	@Override
	public ApiResultResponse<ApiHostModel> getOneHost(String idHost) {
		CoreExchangeService coreExchangeService = new CoreExchangeService(baseUrlReal);
		return coreExchangeService.get("/api/private/u/host/"+idHost, new ParameterizedTypeReference<ApiResultResponse<ApiHostModel>>() {});
	}

	@Override
	public ApiResultResponse<Object> createHost(ApiHostModel apiHostModel) {
		CoreExchangeService coreExchangeService = new CoreExchangeService(baseUrlReal);
		return coreExchangeService.post("/api/private/u/host", new ParameterizedTypeReference<ApiResultResponse<Object>>() {}, apiHostModel);
	}

	@Override
	public ApiResultResponse<Object> updateHost(String idHost,ApiHostModel apiHostModel) {
		CoreExchangeService coreExchangeService = new CoreExchangeService(baseUrlReal);
		return coreExchangeService.put("/api/private/u/host/"+idHost, new ParameterizedTypeReference<ApiResultResponse<Object>>() {}, apiHostModel);
	}

	@Override
	public ApiResultResponse<Object> deletedHost(String idHost) {
		CoreExchangeService coreExchangeService = new CoreExchangeService(baseUrlReal);
		return coreExchangeService.delete("/api/private/u/host/"+idHost, new ParameterizedTypeReference<ApiResultResponse<Object>>() {});
	}

	@Override
	public ApiResultResponse<List<ApiConfigModel>> getConfigs() {
		CoreExchangeService coreExchangeService = new CoreExchangeService(baseUrlReal);
		return coreExchangeService.get("/api/private/u/host/1/config", new ParameterizedTypeReference<ApiResultResponse<List<ApiConfigModel>>>() {});
	}

	@Override
	public ApiResultResponse<ApiConfigDataModel> getFileConfig(String idConfig) {
		CoreExchangeService coreExchangeService = new CoreExchangeService(baseUrlReal);
		return coreExchangeService.get("/api/private/u/host/config/"+idConfig, new ParameterizedTypeReference<ApiResultResponse<ApiConfigDataModel>>() {});
	}

}
