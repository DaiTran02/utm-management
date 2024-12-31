package com.ngn.utm.manager.api.real_tech.authen;

import com.ngn.utm.manager.api.ApiResultResponse;

public interface ApiAuthenService {
	ApiResultResponse<ApiUserRealTechModel> login(ApiUserRealTechModel apiUserRealTechModel);
}
