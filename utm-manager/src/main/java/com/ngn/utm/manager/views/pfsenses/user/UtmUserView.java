package com.ngn.utm.manager.views.pfsenses.user;

import com.ngn.utm.manager.api.pfsenses.user.ApiUserSerivce;
import com.ngn.utm.manager.views.MainLayout;
import com.ngn.utm.manager.views.pfsenses.user.form.UserForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;

@Route(value = "user", layout = MainLayout.class)
@PermitAll
@PageTitle("User")
public class UtmUserView extends VerticalLayout{
	private static final long serialVersionUID = 1L;
	
	public UtmUserView(ApiUserSerivce apiUserSerivce) {
		this.setSizeFull();
		UserForm userForm = new UserForm(apiUserSerivce);
		this.add(userForm);
	}

}
