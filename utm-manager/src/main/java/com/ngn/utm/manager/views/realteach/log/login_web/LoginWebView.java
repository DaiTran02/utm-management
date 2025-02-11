package com.ngn.utm.manager.views.realteach.log.login_web;

import com.ngn.utm.manager.api.real_tech.log.login_web.ApiLoginWebService;
import com.ngn.utm.manager.views.MainLayout;
import com.ngn.utm.manager.views.realteach.log.login_web.form.LoginWebForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;

@Route(value = "login_web", layout = MainLayout.class)
@PageTitle("Login web")
@PermitAll
public class LoginWebView extends VerticalLayout{
	private static final long serialVersionUID = 1L;
	
	public LoginWebView(ApiLoginWebService apiLoginWebService){
		this.setSizeFull();
		
		LoginWebForm loginWebForm = new LoginWebForm(apiLoginWebService);
		this.add(loginWebForm);
	}

}
