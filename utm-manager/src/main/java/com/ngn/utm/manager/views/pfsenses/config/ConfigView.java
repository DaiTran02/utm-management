package com.ngn.utm.manager.views.pfsenses.config;

import com.ngn.utm.manager.api.pfsenses.config.ApiConfigService;
import com.ngn.utm.manager.views.MainLayout;
import com.ngn.utm.manager.views.pfsenses.config.form.ConfigForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;

@Route(value = "config", layout = MainLayout.class)
@PageTitle("Config")
@PermitAll
public class ConfigView extends VerticalLayout{
	private static final long serialVersionUID = 1L;
	
	public ConfigView(ApiConfigService apiConfigService) {
		this.setSizeFull();
		
		ConfigForm configForm = new ConfigForm(apiConfigService);
		this.add(configForm);
		
	}

}
