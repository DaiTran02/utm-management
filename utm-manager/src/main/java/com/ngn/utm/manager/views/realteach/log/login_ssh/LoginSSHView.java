package com.ngn.utm.manager.views.realteach.log.login_ssh;

import com.ngn.utm.manager.api.real_tech.log.login_ssh.ApiLoginSSHSerivce;
import com.ngn.utm.manager.views.MainLayout;
import com.ngn.utm.manager.views.realteach.log.login_ssh.form.LoginSSHForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;

@Route(value = "login_ssh", layout = MainLayout.class)
@PageTitle("Login SSH")
@PermitAll
public class LoginSSHView extends VerticalLayout{
	private static final long serialVersionUID = 1L;
	
	public LoginSSHView(ApiLoginSSHSerivce apiLoginSSHSerivce) {
		this.setSizeFull();
		
		LoginSSHForm loginSSHForm = new LoginSSHForm(apiLoginSSHSerivce);
		this.add(loginSSHForm);
	}

	
}
