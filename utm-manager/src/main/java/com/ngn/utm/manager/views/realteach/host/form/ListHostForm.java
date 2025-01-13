package com.ngn.utm.manager.views.realteach.host.form;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.lineawesome.LineAwesomeIconUrl;

import com.ngn.utm.manager.api.ApiResultResponse;
import com.ngn.utm.manager.api.real_tech.host.ApiHostModel;
import com.ngn.utm.manager.api.real_tech.host.ApiHostService;
import com.ngn.utm.manager.service.FormInterface;
import com.ngn.utm.manager.utils.commons.ButtonTemplate;
import com.ngn.utm.manager.utils.commons.VerticalLayoutTemplate;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class ListHostForm extends VerticalLayoutTemplate implements FormInterface{
	private static final long serialVersionUID = 1L;
	private final ApiHostService apiHostService;
	private Grid<ApiHostModel> grid = new Grid<ApiHostModel>(ApiHostModel.class,false);
	private List<ApiHostModel> listHostModel = new ArrayList<ApiHostModel>();
	
	public ListHostForm(ApiHostService apiHostService) {
		this.apiHostService = apiHostService;
		buildLayout();
		configComponent();
		loadData();
	}
	
	@Override
	public void buildLayout() {
		this.setSizeFull();
		this.add(createLayoutGrid());
	}

	@Override
	public void configComponent() {
		
	}
	
	private void loadData() {
		ApiResultResponse<List<ApiHostModel>> data = apiHostService.getAllHost();
		listHostModel.addAll(data.getData());
		grid.setItems(listHostModel);
	}
	
	private Component createLayoutGrid() {
		grid = new Grid<ApiHostModel>(ApiHostModel.class,false);
		
		grid.addColumn(ApiHostModel::getHostname).setHeader("Tên");
		grid.addColumn(ApiHostModel::getIpAddress).setHeader("Địa chỉ IP");
		grid.addComponentColumn(model->{
			HorizontalLayout hLayout = new HorizontalLayout();
			
			ButtonTemplate btnEdit = new ButtonTemplate(new SvgIcon(LineAwesomeIconUrl.EDIT));
			
			ButtonTemplate btnDeleted = new ButtonTemplate(new SvgIcon(LineAwesomeIconUrl.TRASH_SOLID));
			
			hLayout.add(btnEdit,btnDeleted);
			
			return hLayout;
		});
		
		return grid;
	}

}
