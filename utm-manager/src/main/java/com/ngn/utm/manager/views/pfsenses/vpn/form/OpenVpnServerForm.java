package com.ngn.utm.manager.views.pfsenses.vpn.form;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.ngn.utm.manager.api.pfsenses.ApiResultPfsenseResponse;
import com.ngn.utm.manager.api.pfsenses.vpn.ApiOpenVPNServerModel;
import com.ngn.utm.manager.api.pfsenses.vpn.ApiVPNService;
import com.ngn.utm.manager.service.FormInterface;
import com.ngn.utm.manager.utils.SessionUtil;
import com.ngn.utm.manager.utils.commons.CantConnectToPfsenseForm;
import com.ngn.utm.manager.utils.commons.VerticalLayoutTemplate;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class OpenVpnServerForm extends VerticalLayoutTemplate implements FormInterface{
	private static final long serialVersionUID = 1L;
	private ApiVPNService apiVPNService;
	
	private Grid<ApiOpenVPNServerModel> grid = new Grid<ApiOpenVPNServerModel>(ApiOpenVPNServerModel.class,false);
	private List<ApiOpenVPNServerModel> listModel = new ArrayList<ApiOpenVPNServerModel>();
	private List<Pair<String, String>> listMode = new ArrayList<Pair<String,String>>();
	
	
	public OpenVpnServerForm(ApiVPNService apiVPNService) {
		this.apiVPNService = apiVPNService;
		buildLayout();
		configComponent();
		inItListMode();
		loadData();
	}

	@Override
	public void buildLayout() {
		this.setSizeFull();
		this.add(createGrid());
	}

	@Override
	public void configComponent() {
	}
	
	private void loadData() {
			try {
				ApiResultPfsenseResponse<List<ApiOpenVPNServerModel>> data = apiVPNService.readOpenVPNServer();
				if(data.isSuccess()) {
					listModel.addAll(data.getData());
					SessionUtil.getDeviceInfo().setConnect(true);
				}else {
					SessionUtil.getDeviceInfo().setConnect(false);
				}
				
				grid.setItems(listModel);
				
			} catch (Exception e) {
				this.removeAll();
				CantConnectToPfsenseForm cantConnectToPfsenseForm = new CantConnectToPfsenseForm();
				this.add(cantConnectToPfsenseForm);
			}
	}
	
	private void inItListMode() {
		listMode = new ArrayList<Pair<String,String>>();
		listMode.add(Pair.of("server_tls_user","Remote Access ( SSL/TLS + User Auth )"));
		listMode.add(Pair.of("server_user","Remote Access ( User Auth )"));
		listMode.add(Pair.of("server_tls","Remote Access ( SSL/TLS )"));
		listMode.add(Pair.of("p2p_shared_key"," Peer to Peer ( Shared Key )"));
		listMode.add(Pair.of("p2p_tls"," Peer to Peer ( SSL/TLS )"));
	}
	
	private Component createGrid() {
		grid = new Grid<ApiOpenVPNServerModel>(ApiOpenVPNServerModel.class,false);
		
		grid.addComponentColumn(model->{
			return new Span(model.getProtocol()+"/"+model.getLocal_port()+"( "+model.getDev_mode().toUpperCase()+" )");
		}).setHeader("Protocol/Port").setWidth("150px").setFlexGrow(0);
		grid.addColumn(ApiOpenVPNServerModel::getTunnel_network).setHeader("Tunnel Network").setWidth("150px").setFlexGrow(0);
		grid.addComponentColumn(model->{
			String width = "120px";
			VerticalLayout vLayout = new VerticalLayout();
			vLayout.add(createKeyValue("Mode:", checkKeyMode(model.getMode()), width, true),
					createKeyValue("Data Ciphers:", model.getData_ciphers()+","+model.getData_ciphers_fallback(), width, true),
					createKeyValue("Digest:", model.getDigest(), width, true),
					createKeyValue("D-H Params:", model.getDh_length(), width, true));
			
			return vLayout;
		}).setHeader("Mode/Crypto").setResizable(true);
		
		grid.addColumn(ApiOpenVPNServerModel::getDescription).setHeader("Description").setWidth("200px").setFlexGrow(0).setResizable(true);
		grid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
		
		return grid;
	}
	
	private String checkKeyMode(String mode) {
		for(Pair<String, String> string : listMode) {
			if(string.getKey().equals(mode)) {
				return string.getValue();
			}
		}
		return "";
	}
	
	private Component createKeyValue(String name, String value, String width,boolean isBorder) {
		HorizontalLayout hLayoutKeyValue = new HorizontalLayout();

		H5 header = new H5(name);
		header.getStyle().setMargin("auto 0");
		header.setWidth(width);
		header.getStyle().setFlexShrink("0");

		Span spValue = new Span(value);

		hLayoutKeyValue.add(header,spValue);
		if(isBorder) {
			hLayoutKeyValue.getStyle().setBorderBottom("1px solid rgb(214 214 216)");
		}
		hLayoutKeyValue.setWidthFull();
		

		return hLayoutKeyValue;
	}

}
