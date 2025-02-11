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
import com.vaadin.flow.component.html.Image;
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
	private Image image = new Image("./icons/logo_realtech.png", getCurrentPageTitle());
	
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
        createNavigation();
    }
    
    private void configComponent() {
    	cmbDevice.addValueChangeListener(e->{
    		if(cmbDevice.getValue() != null && cmbDevice.getValue().getKey() != null) {
    			SessionUtil.setDeviceInfo(cmbDevice.getValue().getKey());
    		}
    		createNavigation();
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
        
        image.getStyle().setWidth("100%").setHeight("100%");
        
        Header header = new Header(image);

        Scroller scroller = new Scroller(vLayoutNav);
        
        vLayoutNav.setWidthFull();

        addToDrawer(header, cmbDevice, scroller, createFooter());
    }
    
    public void loadCmbDevice() {
    	List<Pair<ApiHostModel, String>> listDevice = new ArrayList<Pair<ApiHostModel,String>>();
    	
    	listDevice.add(Pair.of(null,""));
    	
    	try {
    		ApiResultResponse<List<ApiHostModel>> dataDevice = apiHostService.getAllHost();
        	dataDevice.getData().forEach(model->{
        		listDevice.add(Pair.of(model,model.getHostname()));
        	});
		} catch (Exception e) {
			// TODO: handle exception
		}
    	
    	cmbDevice.setItems(listDevice);
    	cmbDevice.setItemLabelGenerator(Pair::getValue);
    	if(SessionUtil.getDeviceInfo() != null) {
    		listDevice.forEach(model->{
        		if(model.getKey() != null) {
        			if(SessionUtil.getDeviceInfo().getId() == model.getKey().getId()) {
        				cmbDevice.setValue(model);
        			}
        		}
        	});
    	}else {
    		cmbDevice.setValue(listDevice.get(0));
    	}
    }
    
    public void createNavigation() {
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
    	navLogModule.getStyle().setWidth("100%");
    	
    	
    	SideNavItem itemLogConnectivity = new SideNavItem("Connectivity","connectivity",new SvgIcon(LineAwesomeIconUrl.FILE_ALT));
    	itemLogConnectivity.getStyle().setWidth("100%");
    	navLogModule.addItem(itemLogConnectivity);
    	
    	SideNavItem itemLogIPS = new SideNavItem("IPS","ips",new SvgIcon(LineAwesomeIconUrl.FILE_ALT));
    	itemLogIPS.getStyle().setWidth("100%");
    	navLogModule.addItem(itemLogIPS);
    	
    	SideNavItem itemLogConfig = new SideNavItem("Configs","config_change",new SvgIcon(LineAwesomeIconUrl.FILE_ALT));
    	itemLogConfig.getStyle().setWidth("100%");
    	navLogModule.addItem(itemLogConfig);
    	
    	SideNavItem itemLogCreateGroup = new SideNavItem("Create Group","create_group",new SvgIcon(LineAwesomeIconUrl.USER_FRIENDS_SOLID));
    	itemLogCreateGroup.getStyle().setWidth("100%");
    	navLogModule.addItem(itemLogCreateGroup);
    	
    	SideNavItem itemLogCreateUser = new SideNavItem("Create User","create_user",new SvgIcon(LineAwesomeIconUrl.USER_ALT_SOLID));
    	itemLogCreateUser.getStyle().setWidth("100%");
    	navLogModule.addItem(itemLogCreateUser);
    	
    	SideNavItem itemLogLoginSSH = new SideNavItem("Login SSH","login_ssh",new SvgIcon(LineAwesomeIconUrl.FILE_ALT));
    	itemLogLoginSSH.getStyle().setWidth("100%");
    	navLogModule.addItem(itemLogLoginSSH);
    	
    	SideNavItem itemLogLoginWeb = new SideNavItem("Login Web","login_web",new SvgIcon(LineAwesomeIconUrl.FILE_ALT));
    	itemLogLoginWeb.getStyle().setWidth("100%");
    	navLogModule.addItem(itemLogLoginWeb);
    	
    	navConfigModule.setVisible(false);
    	navLogModule.setVisible(false);
    	
    	if(cmbDevice.getValue().getKey() != null && SessionUtil.getDeviceInfo() != null) {
    		if(SessionUtil.getDeviceInfo().isUseConfigModule()) {
        		navConfigModule.setVisible(true);
        	}else {
        		navConfigModule.setVisible(false);
        	}
        	
        	if(SessionUtil.getDeviceInfo().isUseLogModule()) {
        		navLogModule.setVisible(true);
        	}else {
        		navLogModule.setVisible(false);
        	}
    	}
    	
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
               div.add("Tài khoản: "+user.getUsername());
               div.add(new Icon("lumo", "dropdown"));
               div.getElement().getStyle().set("display", "flex");
               div.getElement().getStyle().set("align-items", "center");
               div.getElement().getStyle().set("gap", "var(--lumo-space-s)");
               userName.add(div);
               userName.getSubMenu().addItem("Đăng xuất", e -> {
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
