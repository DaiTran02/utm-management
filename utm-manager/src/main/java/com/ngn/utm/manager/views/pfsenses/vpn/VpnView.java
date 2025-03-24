package com.ngn.utm.manager.views.pfsenses.vpn;

import com.ngn.utm.manager.api.pfsenses.vpn.ApiVPNService;
import com.ngn.utm.manager.views.MainLayout;
import com.ngn.utm.manager.views.pfsenses.vpn.form.OpenVpnServerForm;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;

@Route(value = "vpn",layout = MainLayout.class)
@PermitAll
@PageTitle("VPN")
public class VpnView extends TabSheet{
	private static final long serialVersionUID = 1L;
	
	public VpnView(ApiVPNService apiVPNService) {
		OpenVpnServerForm openVpnServerForm = new OpenVpnServerForm(apiVPNService);
		this.setSizeFull();
		this.add("OpenVPN/Server", openVpnServerForm);
	}

}
