package com.ngn.utm.manager.api.pfsenses.system;

import com.ngn.utm.manager.api.pfsenses.ApiResultPfsenseResponse;

public interface ApiSystemService {
	ApiResultPfsenseResponse<ApiReadSystemStatusModel> readSystemStatus() throws Exception;
	ApiResultPfsenseResponse<ApiReadSystemHostNameModel> readSystemHost() throws Exception;
	ApiResultPfsenseResponse<ApiReadSystemVersionModel> readSystemVersion() throws Exception;
}
