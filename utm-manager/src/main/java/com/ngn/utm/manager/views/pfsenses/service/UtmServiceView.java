package com.ngn.utm.manager.views.pfsenses.service;

import com.ngn.utm.manager.api.pfsenses.service.ApiServiceOfUtmService;
import com.ngn.utm.manager.views.MainLayout;
import com.ngn.utm.manager.views.pfsenses.service.form.ServiceForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;

@Route(value = "service", layout = MainLayout.class)
@PageTitle("Service")
@PermitAll
public class UtmServiceView extends VerticalLayout{
	private static final long serialVersionUID = 1L;
	
	public UtmServiceView(ApiServiceOfUtmService apiServiceOfUtmService) {
		ServiceForm serviceForm = new ServiceForm(apiServiceOfUtmService);
		
		this.setSizeFull();
		this.add(serviceForm);
	}

}
