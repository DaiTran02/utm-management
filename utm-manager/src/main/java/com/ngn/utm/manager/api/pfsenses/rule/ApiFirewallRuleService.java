package com.ngn.utm.manager.api.pfsenses.rule;

import java.util.List;

import com.ngn.utm.manager.api.pfsenses.ApiResultPfsenseResponse;
import com.nimbusds.jose.shaded.gson.JsonObject;

public interface ApiFirewallRuleService {
	ApiResultPfsenseResponse<List<JsonObject>> readExistingFirewallRules() throws Exception;
	ApiResultPfsenseResponse<Object> createFirewallRule(ApiFirewallRuleInputModel apiFirewallRuleInputModel) throws Exception;
	ApiResultPfsenseResponse<Object> updateFirewallRule(ApiFirewallRuleInputModel apiFirewallRuleInputModel) throws Exception;
	ApiResultPfsenseResponse<Object> deleteFirewallRule(Integer tracker) throws Exception;
}
