package com.ngn.utm.manager.views.home;

import org.vaadin.lineawesome.LineAwesomeIconUrl;

import com.ngn.utm.manager.utils.commons.VerticalLayoutTemplate;
import com.ngn.utm.manager.views.MainLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;

@PageTitle("Hello World")
@Route(value = "dashboard",layout = MainLayout.class)
@Menu(order = 0, icon = LineAwesomeIconUrl.GLOBE_SOLID)
@PermitAll
public class DashBoardView extends VerticalLayoutTemplate{
	private static final long serialVersionUID = 1L;

}
