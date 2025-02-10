package com.ngn.utm.manager.views.realteach.log.create_user;

import com.ngn.utm.manager.api.real_tech.log.create_user.ApiCreateUserService;
import com.ngn.utm.manager.views.MainLayout;
import com.ngn.utm.manager.views.realteach.log.create_user.form.CreateUserForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;

@Route(value = "create_user",layout = MainLayout.class)
@PermitAll
@PageTitle("Create user")
public class CreateUserView extends VerticalLayout{
	private static final long serialVersionUID = 1L;

	public CreateUserView(ApiCreateUserService apiCreateUserService) {
		this.setSizeFull();
		
		CreateUserForm createUserForm = new CreateUserForm(apiCreateUserService);
		this.add(createUserForm);
	}
}
