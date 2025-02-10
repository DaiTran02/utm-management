package com.ngn.utm.manager.views.realteach.log.config_change;

import com.ngn.utm.manager.api.real_tech.log.config_change.ApiLogConfigChangeService;
import com.ngn.utm.manager.utils.commons.VerticalLayoutTemplate;
import com.ngn.utm.manager.views.MainLayout;
import com.ngn.utm.manager.views.realteach.log.config_change.form.LogConfigChangeForm;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;

@Route(value = "config_change",layout = MainLayout.class)
@PermitAll
@PageTitle("Config change")
public class LogConfigChangeView extends VerticalLayoutTemplate{
	private static final long serialVersionUID = 1L;

	public LogConfigChangeView(ApiLogConfigChangeService apiLogConfigChangeService) {
		this.setSizeFull();
		LogConfigChangeForm logConfigChangeForm = new LogConfigChangeForm(apiLogConfigChangeService);
		this.add(logConfigChangeForm);
	}
}
