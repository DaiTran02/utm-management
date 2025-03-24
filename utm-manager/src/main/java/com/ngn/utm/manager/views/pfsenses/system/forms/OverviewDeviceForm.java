package com.ngn.utm.manager.views.pfsenses.system.forms;

import com.ngn.utm.manager.api.pfsenses.ApiResultPfsenseResponse;
import com.ngn.utm.manager.api.pfsenses.system.ApiReadSystemHostNameModel;
import com.ngn.utm.manager.api.pfsenses.system.ApiReadSystemStatusModel;
import com.ngn.utm.manager.api.pfsenses.system.ApiReadSystemVersionModel;
import com.ngn.utm.manager.api.pfsenses.system.ApiSystemService;
import com.ngn.utm.manager.api.pfsenses.utm_interface.ApiInterfaceService;
import com.ngn.utm.manager.api.pfsenses.vpn.ApiVPNService;
import com.ngn.utm.manager.service.FormInterface;
import com.ngn.utm.manager.utils.SessionUtil;
import com.ngn.utm.manager.utils.commons.CantConnectToPfsenseForm;
import com.ngn.utm.manager.utils.commons.HeaderComponent;
import com.ngn.utm.manager.utils.commons.VerticalLayoutTemplate;
import com.ngn.utm.manager.views.pfsenses.utm_interface.form.StatusInterfaceForm;
import com.ngn.utm.manager.views.pfsenses.vpn.form.OpenVpnStatusForm;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class OverviewDeviceForm extends VerticalLayoutTemplate implements FormInterface{
	private static final long serialVersionUID = 1L;
	private final ApiSystemService apiSystemService;
	private ApiInterfaceService apiInterfaceService;
	private ApiVPNService apiVPNService;
	private boolean isRequestSuccess = false;
	
	private HorizontalLayout hLayout = new HorizontalLayout();
	
	public OverviewDeviceForm(ApiSystemService apiSystemService,ApiInterfaceService apiInterfaceService,ApiVPNService apiVPNService) {
		this.apiSystemService = apiSystemService;
		this.apiInterfaceService = apiInterfaceService;
		this.apiVPNService = apiVPNService;
		buildLayout();
		configComponent();
		loadData();
	}

	@Override
	public void buildLayout() {
		this.setPadding(false);
		this.setSizeFull();
		this.add(hLayout);
		hLayout.setWidthFull();
		hLayout.setPadding(false);
		
	}

	@Override
	public void configComponent() {
		
	}
	
	private void loadData() {
		ApiResultPfsenseResponse<ApiReadSystemStatusModel> data = null;
		try {
			data = apiSystemService.readSystemStatus();
			if(data.isSuccess()) {
				isRequestSuccess = true;
				SessionUtil.getDeviceInfo().setConnect(true);
			}else {
				isRequestSuccess = false;
				SessionUtil.getDeviceInfo().setConnect(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(isRequestSuccess) {
			ApiResultPfsenseResponse<ApiReadSystemHostNameModel> dataHostName = null;
			try {
				dataHostName = apiSystemService.readSystemHost();
			} catch (Exception e) {
			} 
			
			
			ApiResultPfsenseResponse<ApiReadSystemVersionModel> dataVersion = null;
			try {
				dataVersion = apiSystemService.readSystemVersion();
			} catch (Exception e) {
			}
			createLayout(data.getData(), dataHostName.getData(),dataVersion.getData());
		}else {
			hLayout.removeAll();
			CantConnectToPfsenseForm connectToPfsenseForm = new CantConnectToPfsenseForm();
			hLayout.add(connectToPfsenseForm);
		}
	}
	
	private void createLayout(ApiReadSystemStatusModel apiReadSystemStatusModel,ApiReadSystemHostNameModel apiReadSystemHostNameModel, ApiReadSystemVersionModel apiReadSystemVersionModel) {
		hLayout.removeAll();
		
		VerticalLayout vLayoutLeft = new VerticalLayout();
		vLayoutLeft.setWidth("50%");
		vLayoutLeft.getStyle().setPaddingRight("0");
		
		HeaderComponent headerSystemInformation = new HeaderComponent("System Information");
		vLayoutLeft.add(headerSystemInformation);
		
		VerticalLayout vlayoutRight = new VerticalLayout();
		vlayoutRight.setWidth("50%");
		vlayoutRight.getStyle().setPaddingLeft("0");
		
		HeaderComponent headerInterface = new HeaderComponent("Interface");
		StatusInterfaceForm statusInterfaceForm = new StatusInterfaceForm(apiInterfaceService);
		headerInterface.addLayout(statusInterfaceForm);
		
		HeaderComponent headerOpenVPN = new HeaderComponent("OpenVPN");
		OpenVpnStatusForm openVpnStatusForm = new OpenVpnStatusForm(apiVPNService);
		headerOpenVPN.addLayout(openVpnStatusForm);
		
		
		vlayoutRight.add(headerInterface,headerOpenVPN);
		
		hLayout.add(vLayoutLeft,vlayoutRight);
		
		
		SystemInformationForm systemInformationForm = new SystemInformationForm(apiReadSystemStatusModel, apiReadSystemHostNameModel,apiReadSystemVersionModel);
		headerSystemInformation.addLayout(systemInformationForm);
	}
}
