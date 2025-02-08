package com.ngn.utm.manager.views.pfsenses.user.form;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.lineawesome.LineAwesomeIconUrl;

import com.ngn.utm.manager.api.pfsenses.ApiResultPfsenseResponse;
import com.ngn.utm.manager.api.pfsenses.user.ApiUserSerivce;
import com.ngn.utm.manager.api.pfsenses.user.ApiUtmUserModel;
import com.ngn.utm.manager.service.FormInterface;
import com.ngn.utm.manager.utils.SessionUtil;
import com.ngn.utm.manager.utils.commons.ButtonTemplate;
import com.ngn.utm.manager.utils.commons.VerticalLayoutTemplate;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class UserForm extends VerticalLayoutTemplate implements FormInterface{
	private static final long serialVersionUID = 1L;
	private ApiUserSerivce apiUserSerivce;
	private boolean isConnect = SessionUtil.getDeviceInfo().isConnect();
	
	private Grid<ApiUtmUserModel> grid = new Grid<ApiUtmUserModel>(ApiUtmUserModel.class,false);
	private List<ApiUtmUserModel> listModel = new ArrayList<ApiUtmUserModel>();
	
	private ButtonTemplate btnCreateNewUser = new ButtonTemplate("New",new SvgIcon(LineAwesomeIconUrl.PLUS_SOLID));

	public UserForm(ApiUserSerivce apiUserSerivce) {
		this.apiUserSerivce = apiUserSerivce;
		buildLayout();
		configComponent();
		loadData();
	}
	
	@Override
	public void buildLayout() {
		this.setSizeFull();
		this.add(btnCreateNewUser,createGrid());
	}

	@Override
	public void configComponent() {
		
	}
	
	private void loadData() {
		listModel = new ArrayList<ApiUtmUserModel>();
		if(isConnect) {
			try {
				ApiResultPfsenseResponse<List<ApiUtmUserModel>> data = apiUserSerivce.readUsers();
				listModel.addAll(data.getData());
			} catch (Exception e) {
			}
			
			grid.setItems(listModel);
			
		}else {
			
		}
	}
	
	private Component createGrid() {
		grid = new Grid<ApiUtmUserModel>(ApiUtmUserModel.class,false);
		grid.addComponentColumn(model->{
			Checkbox checkbox = new Checkbox();
			if(model.getName().equals("admin")) {
				checkbox.setEnabled(false);
			}
			checkbox.addValueChangeListener(e->{
				if(e.getValue()) {
				}else {
				}
			});
			return checkbox;
		}).setWidth("50px").setFlexGrow(0);
		grid.addColumn(ApiUtmUserModel::getName).setHeader("Username");
		grid.addColumn(ApiUtmUserModel::getDescr).setHeader("Full name");
		
		grid.addColumn(ApiUtmUserModel::getGroupname).setHeader("Groups");
		grid.addComponentColumn(model->{
			ButtonTemplate btnEdit = new ButtonTemplate(new SvgIcon(LineAwesomeIconUrl.EDIT));
			btnEdit.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
			btnEdit.getStyle().set("cursor", "pointer");
			btnEdit.addClickListener(e->{
			});
			
			ButtonTemplate btnDelete = new ButtonTemplate(new SvgIcon(LineAwesomeIconUrl.TRASH_ALT_SOLID));
			btnDelete.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
			btnDelete.addThemeVariants(ButtonVariant.LUMO_ERROR);
			btnDelete.getStyle().set("cursor", "pointer");
			if(model.getName().equals("admin")) {
				btnDelete.setEnabled(false);
			}
			
			btnDelete.addClickListener(e->{
			});
			
			return new HorizontalLayout(btnEdit,btnDelete);
		}).setHeader("Actions").setWidth("120px").setFlexGrow(0);
		
		grid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
		
		return grid;
	}

}
