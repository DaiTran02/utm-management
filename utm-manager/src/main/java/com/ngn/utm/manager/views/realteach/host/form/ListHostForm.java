package com.ngn.utm.manager.views.realteach.host.form;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.lineawesome.LineAwesomeIconUrl;

import com.ngn.utm.manager.api.ApiResultResponse;
import com.ngn.utm.manager.api.real_tech.host.ApiHostModel;
import com.ngn.utm.manager.api.real_tech.host.ApiHostService;
import com.ngn.utm.manager.service.FormInterface;
import com.ngn.utm.manager.utils.commons.ButtonTemplate;
import com.ngn.utm.manager.utils.commons.ConfirmDialogTemplate;
import com.ngn.utm.manager.utils.commons.DialogTemplate;
import com.ngn.utm.manager.utils.commons.VerticalLayoutTemplate;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class ListHostForm extends VerticalLayoutTemplate implements FormInterface{
	private static final long serialVersionUID = 1L;
	private final ApiHostService apiHostService;
	private Grid<ApiHostModel> grid = new Grid<ApiHostModel>(ApiHostModel.class,false);
	private List<ApiHostModel> listHostModel = new ArrayList<ApiHostModel>();
	private ButtonTemplate btnCreateHost = new ButtonTemplate("Thêm thiết bị mới",new SvgIcon(LineAwesomeIconUrl.PLUS_SOLID));
	
	public ListHostForm(ApiHostService apiHostService) {
		this.apiHostService = apiHostService;
		buildLayout();
		configComponent();
		loadData();
	}
	
	@Override
	public void buildLayout() {
		this.setSizeFull();
		this.add(btnCreateHost,createLayoutGrid());
	}

	@Override
	public void configComponent() {
		btnCreateHost.addClickListener(e->openDialogCreateHost());
	}
	
	private void loadData() {
		listHostModel = new ArrayList<>();	
		ApiResultResponse<List<ApiHostModel>> data = apiHostService.getAllHost();
		listHostModel.addAll(data.getData());
		grid.setItems(listHostModel);
	}
	
	private Component createLayoutGrid() {
		grid = new Grid<ApiHostModel>(ApiHostModel.class,false);
		grid.addColumn(ApiHostModel::getClientId).setHeader("ID");
		grid.addColumn(ApiHostModel::getHostname).setHeader("Tên");
		grid.addColumn(ApiHostModel::getIpAddress).setHeader("Địa chỉ IP");
		grid.addColumn(ApiHostModel::getMntPort).setHeader("Port");
		
		grid.addComponentColumn(model->{
			HorizontalLayout hLayout = new HorizontalLayout();
			
			ButtonTemplate btnEdit = new ButtonTemplate(new SvgIcon(LineAwesomeIconUrl.EDIT));
			btnEdit.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
			btnEdit.addClickListener(e->openDialogUpdateHost(String.valueOf(model.getId())));
			
			ButtonTemplate btnDeleted = new ButtonTemplate(new SvgIcon(LineAwesomeIconUrl.TRASH_SOLID));
			btnDeleted.addThemeVariants(ButtonVariant.LUMO_ERROR);
			btnDeleted.addClickListener(e->openConfirmDeleted(String.valueOf(model.getId())));
			
			hLayout.add(btnEdit,btnDeleted);
			
			return hLayout;
		}).setHeader("Thao tác").setWidth("100px").setFlexGrow(0);
		
		grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
		
		return grid;
	}

	private void openDialogCreateHost() {
		DialogTemplate dialogTemplate = new DialogTemplate("Thêm thiết bị mới");
		
		HostEditForm hostEditForm = new HostEditForm(apiHostService,null);
		hostEditForm.addChangeListener(e->{
			loadData();
			dialogTemplate.close();
		});
		
		dialogTemplate.getBtnSave().addClickListener(e->{
			hostEditForm.saveHost();
		});
		
		dialogTemplate.add(hostEditForm);
		
		dialogTemplate.open();
	}
	
	private void openDialogUpdateHost(String idHost) {
		DialogTemplate dialogTemplate = new DialogTemplate("Chỉnh sửa thông tin");
		
		HostEditForm hostEditForm = new HostEditForm(apiHostService,idHost);
		hostEditForm.addChangeListener(e->{
			loadData();
			dialogTemplate.close();
		});
		
		dialogTemplate.getBtnSave().addClickListener(e->{
			hostEditForm.saveHost();
		});
		dialogTemplate.setWidth("40%");
		
		dialogTemplate.add(hostEditForm);
		
		dialogTemplate.open();
	}
	
	private void openConfirmDeleted(String idHost) {
		ConfirmDialogTemplate confirmDialogTemplate = new ConfirmDialogTemplate("Xóa thông tin thiết bị");
		confirmDialogTemplate.setText("Xác nhận xóa thông tin thiết bị");
		confirmDialogTemplate.open();
		
	}
	
}
