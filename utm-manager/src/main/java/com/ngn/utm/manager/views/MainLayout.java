package com.ngn.utm.manager.views;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import com.ngn.utm.manager.api.ApiResultResponse;
import com.ngn.utm.manager.api.real_tech.authen.ApiUserRealTechModel;
import com.ngn.utm.manager.api.real_tech.host.ApiHostModel;
import com.ngn.utm.manager.api.real_tech.host.ApiHostService;
import com.ngn.utm.manager.security.AuthenticatedUser;
import com.ngn.utm.manager.utils.SessionUtil;
import com.ngn.utm.manager.utils.commons.VerticalLayoutTemplate;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.server.menu.MenuConfiguration;
import com.vaadin.flow.theme.lumo.LumoUtility;

/**
 * The main view is a top-level placeholder for other views.
 */
//@Layout
//@AnonymousAllowed
public class MainLayout extends AppLayout {
	private static final long serialVersionUID = 1L;
	private final AuthenticatedUser authenticatedUser;
	private ApiHostService apiHostService;
	private ComboBox<Pair<ApiHostModel, String>> cmbDevice = new ComboBox<Pair<ApiHostModel,String>>("Chọn thiết bị");
	
	
	private H1 viewTitle;

    @SuppressWarnings("unused")
	private AccessAnnotationChecker accessChecker;
    private VerticalLayoutTemplate vLayoutNav = new VerticalLayoutTemplate();

    public MainLayout( AccessAnnotationChecker accessChecker,AuthenticatedUser authenticatedUser,ApiHostService apiHostService) {
        this.accessChecker = accessChecker;
        this.authenticatedUser = authenticatedUser;
        this.apiHostService = apiHostService;
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
        loadCmbDevice();
        configComponent();
    }
    
    private void configComponent() {
    	cmbDevice.addValueChangeListener(e->{
    		SessionUtil.setDeviceInfo(cmbDevice.getValue().getKey());
    	});
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

        viewTitle = new H1();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        addToNavbar(true, toggle, viewTitle);
    }

    private void addDrawerContent() {
        Span appName = new Span("UTM Manager");
        appName.addClassNames(LumoUtility.FontWeight.SEMIBOLD, LumoUtility.FontSize.LARGE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(vLayoutNav);
        
        vLayoutNav.setWidthFull();
        createNavigation();

        addToDrawer(header, cmbDevice, scroller, createFooter());
    }
    
    private void loadCmbDevice() {
    	List<Pair<ApiHostModel, String>> listDevice = new ArrayList<Pair<ApiHostModel,String>>();
    	ApiResultResponse<List<ApiHostModel>> dataDevice = apiHostService.getAllHost();
    	dataDevice.getData().forEach(model->{
    		listDevice.add(Pair.of(model,model.getHostname()));
    	});
    	
    	cmbDevice.setItems(listDevice);
    	cmbDevice.setItemLabelGenerator(Pair::getValue);
    	cmbDevice.setValue(listDevice.get(0));
    	SessionUtil.setDeviceInfo(cmbDevice.getValue().getKey());
    	
    }
    
    private void createNavigation() {
    	vLayoutNav.removeAll();
    	
    	//Home
    	
    	SideNavItem sideHome = new SideNavItem("Tổng quan", "/dashboard",new SvgIcon(LineAwesomeIconUrl.DASHCUBE));
    	sideHome.getStyle().setWidth("100%");
    	vLayoutNav.add(sideHome);
    	
    	SideNavItem sideHost = new SideNavItem("Thiết bị", "/host",new SvgIcon(LineAwesomeIconUrl.LIST_SOLID));
    	vLayoutNav.add(sideHost);
    	sideHost.getStyle().setWidth("100%");
    	
    	//Pfsense
    	
    	SideNav navConfigModule = new SideNav("Chi tiết thiết bị");
    	navConfigModule.getStyle().setWidth("100%");
    	navConfigModule.setCollapsible(true);
    	
    	SideNavItem itemDevice = new SideNavItem("Tổng quan thiết bị","overview_device", new SvgIcon(LineAwesomeIconUrl.DESKTOP_SOLID));
    	itemDevice.getStyle().setWidth("100%");
    	navConfigModule.addItem(itemDevice);
    	
    	SideNavItem itemAlias = new SideNavItem("Bí danh","alias",new SvgIcon(LineAwesomeIconUrl.BOOK_SOLID));
    	itemAlias.getStyle().setWidth("100%");
    	navConfigModule.addItem(itemAlias);
    	
    	SideNavItem itemRule = new SideNavItem("Rule","rule",new SvgIcon(LineAwesomeIconUrl.BOOK_OPEN_SOLID));
    	itemRule.getStyle().setWidth("100%");
    	navConfigModule.addItem(itemRule);
    	
    	SideNavItem itemService = new SideNavItem("Service","service",new SvgIcon(LineAwesomeIconUrl.FILE_CODE_SOLID));
    	itemService.getStyle().setWidth("100%");
    	navConfigModule.addItem(itemService);
    	
    	SideNavItem itemLog = new SideNavItem("Log","utm_log",new SvgIcon(LineAwesomeIconUrl.HISTORY_SOLID));
    	itemLog.getStyle().setWidth("100%");
    	navConfigModule.addItem(itemLog);
    	
    	SideNavItem itemConfig = new SideNavItem("Config","config",new SvgIcon(LineAwesomeIconUrl.DESKPRO));
    	itemConfig.getStyle().setWidth("100%");
    	navConfigModule.addItem(itemConfig);
    	
    	SideNavItem itemUser = new SideNavItem("User","user",new SvgIcon(LineAwesomeIconUrl.USER));
    	itemUser.getStyle().setWidth("100%");
    	navConfigModule.addItem(itemUser);
    	
    	vLayoutNav.add(navConfigModule);
    	//LogRealtech
    	SideNav navLogModule = new SideNav("Quản lý log");
    	navLogModule.setCollapsible(true);
    	SideNavItem item1 = new SideNavItem("Connectivity");
    	item1.getStyle().setWidth("100%");
    	navLogModule.addItem(item1);
    	
    	vLayoutNav.add(navLogModule);
    	
    	
    }

//    private SideNav createNavigation() {
//        SideNav nav = new SideNav();
//        SideNav sideNavConfigModule = new SideNav("Quản lý thiết bị");
//        
//        SideNav sideNavLogModule = new SideNav("Quản lý log");
//
//        List<MenuEntry> menuEntries = MenuConfiguration.getMenuEntries();
//        menuEntries.forEach(entry -> {
//            if (entry.icon() != null) {
//                nav.addItem(new SideNavItem(entry.title(), entry.path(), new SvgIcon(entry.icon())));
//            } else {
//                nav.addItem(new SideNavItem(entry.title(), entry.path()));
//            }
//            
//            System.out.println(entry);
//            
//
//            
//        });
//
//        return nav;
//    }

    private Footer createFooter() {
        Footer layout = new Footer();
        
        Optional<ApiUserRealTechModel> maybeUser = authenticatedUser.get();
        if(maybeUser.isPresent()) {
        	ApiUserRealTechModel user = maybeUser.get();

               Avatar avatar = new Avatar(user.getUsername());
               avatar.setThemeName("xsmall");
               avatar.getElement().setAttribute("tabindex", "-1");

               MenuBar userMenu = new MenuBar();
               userMenu.setThemeName("tertiary-inline contrast");

               MenuItem userName = userMenu.addItem("");
               Div div = new Div();
               div.add(avatar);
               div.add(user.getUsername());
               div.add(new Icon("lumo", "dropdown"));
               div.getElement().getStyle().set("display", "flex");
               div.getElement().getStyle().set("align-items", "center");
               div.getElement().getStyle().set("gap", "var(--lumo-space-s)");
               userName.add(div);
               userName.getSubMenu().addItem("Sign out", e -> {
                   authenticatedUser.logout();
               });

               layout.add(userMenu);
        }else {
            Anchor loginLink = new Anchor("login", "Sign in");
            layout.add(loginLink);
        }

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        return MenuConfiguration.getPageHeader(getContent()).orElse("");
    }
}
