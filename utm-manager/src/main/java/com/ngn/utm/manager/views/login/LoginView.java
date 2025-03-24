package com.ngn.utm.manager.views.login;

import com.ngn.utm.manager.security.AuthenticatedUser;
import com.ngn.utm.manager.service.FormInterface;
import com.ngn.utm.manager.views.realteach.host.HostView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;


@PageTitle("Login")
@Route(value = "login")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver, FormInterface {
	private static final long serialVersionUID = 1L;

	private final AuthenticatedUser authenticatedUser;

	private LoginOverlay loginOverlay = new LoginOverlay();
	

	public LoginView(AuthenticatedUser authenticatedUser) {
		this.authenticatedUser = authenticatedUser;

		buildLayout();
		configComponent();
		setLocalchange();

		//        setAction(RouteUtil.getRoutePath(VaadinService.getCurrent().getContext(), getClass()));

	}

	@Override
	public void buildLayout() {
		this.setSizeFull();
		this.add(loginOverlay);

		Image image = new Image("./themes/favicon/logo.png", "img");
		image.getStyle().setMarginLeft("10px");
		
		
//		Div div = new Div();
//		div.setId("id_login");
		
		loginOverlay.setId("id_login");
		
//		Html html = new Html("<script>"
//			+ "VANTA.NET({ "
//			+ "  el: '#id_login', "
//			+ "  mouseControls: true, "
//			+ "  touchControls: true, "
//			+ "  gyroControls: false, "
//			+ "  minHeight: 200.00, "
//			+ "  minWidth: 200.00, "
//			+ "  scale: 1.00, "
//			+ "  scaleMobile: 1.00 "
//			+ "});"
//			+ "</script>");
//		this.add(html);
		
//		UI.getCurrent().getPage().executeJs(
//			    "setTimeout(() => { " +
//			    "  VANTA.NET({ " +
//			    "    el: document.querySelector('vaadin-login-overlay-wrapper'), " + // Chọn phần tử đúng
//			    "    mouseControls: true, " +
//			    "    touchControls: true, " +
//			    "    gyroControls: false, " +
//			    "    minHeight: 200.00, " +
//			    "    minWidth: 200.00, " +
//			    "    scale: 1.00, " +
//			    "    scaleMobile: 1.00 " +
//			    "  });" +
//			    "}, 1000);"
//			);


		loginOverlay.setTitle(image);
		loginOverlay.setForgotPasswordButtonVisible(false);
		loginOverlay.setOpened(true);
	}

	@Override
	public void configComponent() {
		loginOverlay.addLoginListener(e->{
			String userName = e.getUsername();
			String password = e.getPassword();
			if(authenticate(userName, password)) {
				UI.getCurrent().navigate(HostView.class);
			}else {
				loginOverlay.setError(true);
			}
		});
	}

	private void setLocalchange() {
		LoginI18n i18n = LoginI18n.createDefault();
		i18n.setHeader(new LoginI18n.Header());
		i18n.getHeader().setTitle("UTM Manager");
		i18n.getHeader().setDescription("UTM Manager");
		i18n.setAdditionalInformation(null);

		LoginI18n.Form i18nForm = i18n.getForm();
		i18nForm.setForgotPassword("Quên mật khẩu");
		i18nForm.setPassword("Mật khẩu");
		i18nForm.setSubmit("Đăng nhập");
		i18nForm.setUsername("Tên đăng nhập");
		i18nForm.setTitle("Đăng nhập");

		loginOverlay.setI18n(i18n);
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		//        if (authenticatedUser.get().isPresent()) {
		//            // Already logged in
		//            setOpened(false);
		//            event.forwardTo("");
		//        }

		loginOverlay.setError(event.getLocation().getQueryParameters().getParameters().containsKey(""));
	}

	private boolean authenticate(String userName,String password) {
		return authenticatedUser.authenticate(userName, password);
	}


}
