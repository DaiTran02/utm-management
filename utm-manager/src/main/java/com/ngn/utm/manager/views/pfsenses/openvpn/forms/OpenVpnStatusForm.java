package com.ngn.utm.manager.views.pfsenses.openvpn.forms;

import java.util.ArrayList;
import java.util.List;

import com.ngn.utm.manager.api.pfsenses.openvpn.ApiReadOpenVpnStatusModel;
import com.ngn.utm.manager.service.FormInterface;
import com.ngn.utm.manager.utils.commons.VerticalLayoutTemplate;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Span;

public class OpenVpnStatusForm extends VerticalLayoutTemplate implements FormInterface{
	private static final long serialVersionUID = 1L;
	
	private Grid<ApiReadOpenVpnStatusModel.Server> grid = new Grid<ApiReadOpenVpnStatusModel.Server>(ApiReadOpenVpnStatusModel.Server.class,false);
	private List<ApiReadOpenVpnStatusModel.Server> listModel = new ArrayList<ApiReadOpenVpnStatusModel.Server>();
	public OpenVpnStatusForm() {
		buildLayout();
		configComponent();
	}
	
	@Override
	public void buildLayout() {
		this.setPadding(false);
		this.add(createGrid());
		
	}

	@Override
	public void configComponent() {
		
	}
	
	public void loadData(ApiReadOpenVpnStatusModel apiReadOpenVpnStatusModel) {
		listModel = new ArrayList<ApiReadOpenVpnStatusModel.Server>();
		listModel.addAll(apiReadOpenVpnStatusModel.getServers());
		for(ApiReadOpenVpnStatusModel.Server server : listModel) {
			System.out.println(server);
		}
		
		grid.setItems(listModel);
	}
	
	
	private Component createGrid() {
		grid = new Grid<ApiReadOpenVpnStatusModel.Server>(ApiReadOpenVpnStatusModel.Server.class,false);
		grid.addComponentColumn(model->{
			return new Span(model.getPort());
		}).setHeader("Port");
		
		grid.addComponentColumn(model->{
			return new Span(model.getMode());
		}).setHeader("Mode");
		
		grid.setAllRowsVisible(true);
		grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
		grid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
		grid.addClassName("utm-grid");
		grid.setMaxHeight("500px");
		grid.setSizeFull();
		return grid;
	}

}
