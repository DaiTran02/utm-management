package com.ngn.utm.manager.api.pfsenses.openvpn;

import com.ngn.utm.manager.api.pfsenses.ApiResultPfsenseResponse;

public interface ApiOpenVpnService {
	ApiResultPfsenseResponse<ApiReadOpenVpnStatusModel> readOpenVpnStatus() throws Exception;
}
