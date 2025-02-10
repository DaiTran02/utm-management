package com.ngn.utm.manager.views.realteach.log.create_group;

import com.ngn.utm.manager.api.real_tech.log.create_group.ApiLogCreateGroupService;
import com.ngn.utm.manager.views.MainLayout;
import com.ngn.utm.manager.views.realteach.log.create_group.form.CreateGroupForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;

@Route(value = "create_group",layout = MainLayout.class)
@PermitAll
@PageTitle("Create group")
public class CreateGroupView extends VerticalLayout{
	private static final long serialVersionUID = 1L;
	
	public CreateGroupView(ApiLogCreateGroupService apiLogCreateGroupService) {
		this.setSizeFull();
		CreateGroupForm createGroup = new CreateGroupForm(apiLogCreateGroupService);
		this.add(createGroup);
	}

}
