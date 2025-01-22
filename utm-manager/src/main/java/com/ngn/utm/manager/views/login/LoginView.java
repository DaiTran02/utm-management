package com.ngn.utm.manager.views.login;

import com.ngn.utm.manager.security.AuthenticatedUser;
import com.ngn.utm.manager.service.FormInterface;
import com.ngn.utm.manager.views.realteach.host.HostView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;


@PageTitle("Login")
@Route(value = "login")
@AnonymousAllowed
public class LoginView extends LoginOverlay implements BeforeEnterObserver, FormInterface {
	private static final long serialVersionUID = 1L;
	
	private final AuthenticatedUser authenticatedUser;

    public LoginView(AuthenticatedUser authenticatedUser) {
    	this.authenticatedUser = authenticatedUser;
    	
    	buildLayout();
    	configComponent();
    	
//        setAction(RouteUtil.getRoutePath(VaadinService.getCurrent().getContext(), getClass()));

        LoginI18n i18n = LoginI18n.createDefault();
        i18n.setHeader(new LoginI18n.Header());
        i18n.getHeader().setTitle("UTM Manager");
        i18n.getHeader().setDescription("Login using user/user or admin/admin");
        i18n.setAdditionalInformation(null);
        setI18n(i18n);

        setForgotPasswordButtonVisible(false);
        setOpened(true);
    }
    
	@Override
	public void buildLayout() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configComponent() {
		this.addLoginListener(e->{
			String userName = e.getUsername();
			String password = e.getPassword();
			if(authenticate(userName, password)) {
				UI.getCurrent().navigate(HostView.class);
			}
		});
	}

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
//        if (authenticatedUser.get().isPresent()) {
//            // Already logged in
//            setOpened(false);
//            event.forwardTo("");
//        }

        setError(event.getLocation().getQueryParameters().getParameters().containsKey(""));
    }
    
    private boolean authenticate(String userName,String password) {
    	return authenticatedUser.authenticate(userName, password);
    }


}
