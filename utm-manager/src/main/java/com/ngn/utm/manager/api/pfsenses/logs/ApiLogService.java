package com.ngn.utm.manager.api.pfsenses.logs;

import java.util.List;

import com.ngn.utm.manager.api.pfsenses.ApiResultPfsenseResponse;

public interface ApiLogService {
	ApiResultPfsenseResponse<List<String>> readSystemLog() throws Exception;
	ApiResultPfsenseResponse<List<String>> readFirewallLog() throws Exception;
	ApiResultPfsenseResponse<List<String>> readDHCPLog() throws Exception;
}
