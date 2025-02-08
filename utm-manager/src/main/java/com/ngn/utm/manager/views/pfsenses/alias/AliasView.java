package com.ngn.utm.manager.views.pfsenses.alias;

import com.ngn.utm.manager.api.pfsenses.alias.ApiAliasService;
import com.ngn.utm.manager.views.MainLayout;
import com.ngn.utm.manager.views.pfsenses.alias.form.FirewallAliasIpForm;
import com.ngn.utm.manager.views.pfsenses.alias.form.FirewallAliasPortForm;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;

@Route(value = "alias", layout = MainLayout.class)
@PageTitle("Bí danh")
@PermitAll
public class AliasView extends TabSheet{
	private static final long serialVersionUID = 1L;
	
	public AliasView(ApiAliasService apiAliasService) {
		this.setSizeFull();
		FirewallAliasIpForm firewallAliasIpForm = new FirewallAliasIpForm(apiAliasService);
		this.add("IP", firewallAliasIpForm);
		
		FirewallAliasPortForm firewallAliasPortForm = new FirewallAliasPortForm(apiAliasService);
		this.add("Port", firewallAliasPortForm);
		
		this.addSelectedChangeListener(e->{
			if(this.getSelectedIndex() == 1) {
				firewallAliasPortForm.loadData();
			}
		});
	}

}
