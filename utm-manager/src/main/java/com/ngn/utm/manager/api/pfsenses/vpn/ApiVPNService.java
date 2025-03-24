	package com.ngn.utm.manager.api.pfsenses.vpn;

import java.util.List;

import com.ngn.utm.manager.api.pfsenses.ApiResultPfsenseResponse;

public interface ApiVPNService {
	ApiResultPfsenseResponse<List<ApiOpenVPNServerModel>> readOpenVPNServer() throws Exception;
	ApiResultPfsenseResponse<ApiReadOpenVpnStatusModel> readOpenVPNStatus() throws Exception;
}
