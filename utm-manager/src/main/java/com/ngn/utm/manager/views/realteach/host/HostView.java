package com.ngn.utm.manager.views.realteach.host;

import org.vaadin.lineawesome.LineAwesomeIconUrl;

import com.ngn.utm.manager.api.real_tech.host.ApiHostService;
import com.ngn.utm.manager.views.MainLayout;
import com.ngn.utm.manager.views.realteach.host.form.ListHostForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;

@PermitAll
@PageTitle("Danh sách thiết bị")
@Route(value = "host",layout = MainLayout.class)
@Menu(order = 1, icon = LineAwesomeIconUrl.GLOBE_SOLID)
public class HostView extends VerticalLayout{
	private static final long serialVersionUID = 1L;
	
	public HostView(ApiHostService apiHostService) {
		ListHostForm listHostForm = new ListHostForm(apiHostService);
		this.add(listHostForm);
	}

}
