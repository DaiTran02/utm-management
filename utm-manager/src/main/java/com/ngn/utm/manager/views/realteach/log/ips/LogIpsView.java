package com.ngn.utm.manager.views.realteach.log.ips;

import com.ngn.utm.manager.api.real_tech.log.ips.ApiLogIpsService;
import com.ngn.utm.manager.views.MainLayout;
import com.ngn.utm.manager.views.realteach.log.ips.form.LogIpsForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;

@PermitAll
@Route(value = "ips",layout = MainLayout.class)
@PageTitle("IPS")
public class LogIpsView extends VerticalLayout{
	private static final long serialVersionUID = 1L;
	
	public LogIpsView(ApiLogIpsService apiLogIpsService) {
		this.setSizeFull();
		LogIpsForm logIpsForm = new LogIpsForm(apiLogIpsService);
		this.add(logIpsForm);
	}

}
