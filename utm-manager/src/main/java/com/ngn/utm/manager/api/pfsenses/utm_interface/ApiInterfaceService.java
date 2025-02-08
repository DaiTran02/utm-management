package com.ngn.utm.manager.api.pfsenses.utm_interface;

import java.util.List;

import com.ngn.utm.manager.api.pfsenses.ApiResultPfsenseResponse;

public interface ApiInterfaceService {
	ApiResultPfsenseResponse<List<ApiReadInterfaceModel>> readInterfaceStatus() throws Exception;
}
