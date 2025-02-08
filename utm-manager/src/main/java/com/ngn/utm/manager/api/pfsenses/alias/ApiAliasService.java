package com.ngn.utm.manager.api.pfsenses.alias;

import java.util.List;

import com.ngn.utm.manager.api.pfsenses.ApiResultPfsenseResponse;

public interface ApiAliasService {
	ApiResultPfsenseResponse<List<ApiReadFirewallAliasModel>> readFirewallAliases() throws Exception;
	ApiResultPfsenseResponse<Object> deleteFirewallAlias(String idAlies) throws Exception;
	ApiResultPfsenseResponse<Object> createFirewallAlias(ApiCreateAndUpdateAliasModel apiCreateAndUpdateAliasModel) throws Exception;
	ApiResultPfsenseResponse<Object> updateFirewallAlias(ApiCreateAndUpdateAliasModel apiCreateAndUpdateAliasModel) throws Exception;
}
