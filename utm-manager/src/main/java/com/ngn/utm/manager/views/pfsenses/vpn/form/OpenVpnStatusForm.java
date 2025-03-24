package com.ngn.utm.manager.views.pfsenses.vpn.form;

import java.util.ArrayList;
import java.util.List;

import com.ngn.utm.manager.api.pfsenses.ApiResultPfsenseResponse;
import com.ngn.utm.manager.api.pfsenses.vpn.ApiReadOpenVpnStatusModel;
import com.ngn.utm.manager.api.pfsenses.vpn.ApiVPNService;
import com.ngn.utm.manager.service.FormInterface;
import com.ngn.utm.manager.utils.SessionUtil;
import com.ngn.utm.manager.utils.commons.VerticalLayoutTemplate;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;

public class OpenVpnStatusForm extends VerticalLayoutTemplate implements FormInterface{
	private static final long serialVersionUID = 1L;
	private ApiVPNService apiVPNService;
	private Grid<ApiReadOpenVpnStatusModel.Server> grid = new Grid<ApiReadOpenVpnStatusModel.Server>(ApiReadOpenVpnStatusModel.Server.class,false);
	private List<ApiReadOpenVpnStatusModel.Server> listModel = new ArrayList<ApiReadOpenVpnStatusModel.Server>();

	public OpenVpnStatusForm(ApiVPNService apiVPNService) {
		this.apiVPNService = apiVPNService;
		buildLayout();
		configComponent();
		loadData();
	}
	
	@Override
	public void buildLayout() {
		this.setWidthFull();
		this.add(createGrid());
		
	}

	@Override
	public void configComponent() {
		
	}
	
	private void loadData() {
		listModel = new ArrayList<ApiReadOpenVpnStatusModel.Server>();
			try {
				ApiResultPfsenseResponse<ApiReadOpenVpnStatusModel> data = apiVPNService.readOpenVPNStatus();
				if(data.isSuccess()) {
					listModel.addAll(data.getData().getServers());
					SessionUtil.getDeviceInfo().setConnect(true);
				}else {
					SessionUtil.getDeviceInfo().setConnect(false);
				}
				grid.setItems(listModel);
			} catch (Exception e) {
			}
	}
	
	private Component createGrid() {
		grid = new Grid<ApiReadOpenVpnStatusModel.Server>(ApiReadOpenVpnStatusModel.Server.class,false);
		
		grid.addColumn(ApiReadOpenVpnStatusModel.Server::getName).setHeader("Name");
		grid.addColumn(ApiReadOpenVpnStatusModel.Server::getPort).setHeader("Port");
		grid.addColumn(ApiReadOpenVpnStatusModel.Server::getMode).setHeader("Mode");
		grid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
		grid.setHeight("auto");
		return grid;
	}
	
	

}
