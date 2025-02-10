package com.ngn.utm.manager.views.realteach.log.connectivity;

import com.ngn.utm.manager.api.real_tech.log.connectivity.ApiLogConnectivityService;
import com.ngn.utm.manager.views.MainLayout;
import com.ngn.utm.manager.views.realteach.log.connectivity.form.LogConnectivityForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;

@Route(value = "connectivity", layout = MainLayout.class)
@PermitAll
@PageTitle("Connectivity")
public class ConnectivityView extends VerticalLayout{
	private static final long serialVersionUID = 1L;
	
	public ConnectivityView(ApiLogConnectivityService apiLogConnectivityService) {
		this.setSizeFull();
		
		LogConnectivityForm logConnectivityFrom = new LogConnectivityForm(apiLogConnectivityService);
		this.add(logConnectivityFrom);
		
	}

}
