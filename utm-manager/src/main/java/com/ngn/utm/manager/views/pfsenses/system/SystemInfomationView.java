package com.ngn.utm.manager.views.pfsenses.system;

import com.ngn.utm.manager.api.pfsenses.system.ApiSystemService;
import com.ngn.utm.manager.api.pfsenses.utm_interface.ApiInterfaceService;
import com.ngn.utm.manager.api.pfsenses.vpn.ApiVPNService;
import com.ngn.utm.manager.utils.commons.VerticalLayoutTemplate;
import com.ngn.utm.manager.views.MainLayout;
import com.ngn.utm.manager.views.pfsenses.system.forms.OverviewDeviceForm;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;

@Route(value = "overview_device", layout = MainLayout.class)
@PermitAll
@PageTitle("Tổng quan thiết bị")
public class SystemInfomationView extends VerticalLayoutTemplate{
	private static final long serialVersionUID = 1L;
	
	public SystemInfomationView(ApiSystemService apiSystemService,ApiInterfaceService apiInterfaceService,ApiVPNService apiVPNService) {
		this.setPadding(false);
		OverviewDeviceForm systemStatusForm = new OverviewDeviceForm(apiSystemService,apiInterfaceService,apiVPNService);
		this.add(systemStatusForm);
	}

}
