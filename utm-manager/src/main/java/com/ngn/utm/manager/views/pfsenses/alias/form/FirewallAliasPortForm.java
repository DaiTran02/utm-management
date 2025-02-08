package com.ngn.utm.manager.views.pfsenses.alias.form;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.lineawesome.LineAwesomeIconUrl;

import com.ngn.utm.manager.api.pfsenses.ApiResultPfsenseResponse;
import com.ngn.utm.manager.api.pfsenses.alias.ApiAliasService;
import com.ngn.utm.manager.api.pfsenses.alias.ApiReadFirewallAliasModel;
import com.ngn.utm.manager.service.FormInterface;
import com.ngn.utm.manager.utils.SessionUtil;
import com.ngn.utm.manager.utils.commons.ButtonTemplate;
import com.ngn.utm.manager.utils.commons.VerticalLayoutTemplate;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class FirewallAliasPortForm extends VerticalLayoutTemplate implements FormInterface{
	private static final long serialVersionUID = 1L;
	private ApiAliasService apiAliasService;
	
	private boolean isConnect = SessionUtil.getDeviceInfo().isConnect();
	private Grid<ApiReadFirewallAliasModel> grid = new Grid<ApiReadFirewallAliasModel>(ApiReadFirewallAliasModel.class,false);
	private List<ApiReadFirewallAliasModel> listModel = new ArrayList<ApiReadFirewallAliasModel>();
	private ButtonTemplate btnNew = new ButtonTemplate("Add",new SvgIcon(LineAwesomeIconUrl.PLUS_SOLID));
	
	public FirewallAliasPortForm(ApiAliasService apiAliasService) {
		this.apiAliasService = apiAliasService;
		buildLayout();
		configComponent();
	}

	@Override
	public void buildLayout() {
		this.setSizeFull();
		this.add(btnNew);
		this.add(createGrid());
	}

	@Override
	public void configComponent() {
		
	}
	
	public void loadData() {
		listModel = new ArrayList<ApiReadFirewallAliasModel>();
		if(isConnect) {
			try {
				ApiResultPfsenseResponse<List<ApiReadFirewallAliasModel>> data = apiAliasService.readFirewallAliases();
				for(ApiReadFirewallAliasModel firewallAliasModel : data.getData()) {
					if(firewallAliasModel.getType().equals("port")) {
						listModel.add(firewallAliasModel);
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			grid.setItems(listModel);
			
		}else {
			this.removeAll();
		}
	}
	
	private Component createGrid() {
		grid = new Grid<ApiReadFirewallAliasModel>(ApiReadFirewallAliasModel.class,false);
		
		grid.addColumn(ApiReadFirewallAliasModel::getName).setHeader("Name").setWidth("120px").setFlexGrow(0);
		grid.addComponentColumn(model->{
			Span label = new Span();
			label.setText(model.getAddress());
			label.setText(label.getText().replace(" ", "  ,  "));

			return label;

		}).setHeader("Values");
		grid.addColumn(ApiReadFirewallAliasModel::getDescr).setHeader("Description");
		grid.addComponentColumn(model->{
			HorizontalLayout hLayoutButton = new HorizontalLayout();
			ButtonTemplate btnEdit = new ButtonTemplate("Edit",new SvgIcon(LineAwesomeIconUrl.EDIT));
			
			ButtonTemplate btnDelete = new ButtonTemplate(new SvgIcon(LineAwesomeIconUrl.TRASH_ALT_SOLID));
			
			hLayoutButton.add(btnEdit,btnDelete);
			
			return hLayoutButton;
		}).setWidth("150px").setFlexGrow(0).setHeader("Thao tác");
		
		grid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
		
		return grid;
	}

}
