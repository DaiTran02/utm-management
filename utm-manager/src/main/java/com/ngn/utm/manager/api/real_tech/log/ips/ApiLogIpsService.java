package com.ngn.utm.manager.api.real_tech.log.ips;

import com.ngn.utm.manager.api.ApiResultResponse;

public interface ApiLogIpsService {
	ApiResultResponse<ApiDataLogIpsStaticModel> getIpsStatictisByFilter(ApiFilterLogIpsModel apiFilterLogIpsModel);
	ApiResultResponse<ApiExportLogIpsModel> exportIpsStatictisByFilter(ApiFilterLogIpsModel apiFilterLogIpsModel);
	ApiResultResponse<ApiDataLogIpsModel> getIpsByFilter(ApiFilterLogIpsModel apiFilterLogIpsModel);
	ApiResultResponse<ApiExportLogIpsModel> exportIpsByFilter(ApiFilterLogIpsModel apiFilterLogIpsModel);
	ApiResultResponse<ApiLogIpsModel> getIpsById(String id);
}
