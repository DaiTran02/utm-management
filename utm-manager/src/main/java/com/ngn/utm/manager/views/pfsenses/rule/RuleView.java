package com.ngn.utm.manager.views.pfsenses.rule;

import java.util.List;
import java.util.Map;

import com.ngn.utm.manager.api.pfsenses.rule.ApiExistingFirewallRuleModel;
import com.ngn.utm.manager.api.pfsenses.rule.ApiFirewallRuleService;
import com.ngn.utm.manager.api.pfsenses.utm_interface.ApiInterfaceService;
import com.ngn.utm.manager.api.pfsenses.utm_interface.ApiStatusInterfaceModel;
import com.ngn.utm.manager.views.MainLayout;
import com.ngn.utm.manager.views.pfsenses.rule.form.FirewallRuleForm;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;

@Route(value = "rule",layout = MainLayout.class)
@PageTitle("Rules")
@PermitAll
public class RuleView extends TabSheet{
	private static final long serialVersionUID = 1L;
	
	public RuleView(ApiInterfaceService apiInterfaceService,ApiFirewallRuleService apiFirewallRuleService) {
		this.setSizeFull();
		
		FirewallRuleForm firewallRuleForm = new FirewallRuleForm(apiFirewallRuleService, apiInterfaceService);
		
		for(Map.Entry<String, List<ApiExistingFirewallRuleModel>> map : firewallRuleForm.getMapData().entrySet()) {
			String name = checkInterface(map.getKey(), firewallRuleForm.getListInterfaces());
			this.add(name, firewallRuleForm.createLayout("rules",map.getValue()));
		}
	}
	
	private String checkInterface(String key,List<ApiStatusInterfaceModel> data) {
		for(ApiStatusInterfaceModel model : data) {
			if(key.equals(model.getName())) {
				return model.getDescr();
			}
		}
		return "Unknow";
	}

}
