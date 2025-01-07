package com.ngn.utm.manager.views.home;

import org.vaadin.lineawesome.LineAwesomeIconUrl;

import com.ngn.utm.manager.views.MainLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@PageTitle("Hello World")
@Route(value = "",layout = MainLayout.class)
@Menu(order = 0, icon = LineAwesomeIconUrl.GLOBE_SOLID)
@RolesAllowed("USER")
public class DashBoardView {

}
