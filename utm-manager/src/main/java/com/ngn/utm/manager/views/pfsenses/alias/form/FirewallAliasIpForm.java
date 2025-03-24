package com.ngn.utm.manager.views.pfsenses.alias.form;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.lineawesome.LineAwesomeIconUrl;

import com.ngn.utm.manager.api.pfsenses.ApiResultPfsenseResponse;
import com.ngn.utm.manager.api.pfsenses.alias.ApiAliasService;
import com.ngn.utm.manager.api.pfsenses.alias.ApiReadFirewallAliasModel;
import com.ngn.utm.manager.service.FormInterface;
import com.ngn.utm.manager.utils.commons.ButtonTemplate;
import com.ngn.utm.manager.utils.commons.CantConnectToPfsenseForm;
import com.ngn.utm.manager.utils.commons.ConfirmDialogTemplate;
import com.ngn.utm.manager.utils.commons.DialogTemplate;
import com.ngn.utm.manager.utils.commons.NotificationTemplate;
import com.ngn.utm.manager.utils.commons.VerticalLayoutTemplate;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class FirewallAliasIpForm extends VerticalLayoutTemplate implements FormInterface{
	private static final long serialVersionUID = 1L;
	private ApiAliasService apiAliasService;
	
	private Grid<ApiReadFirewallAliasModel> grid = new Grid<ApiReadFirewallAliasModel>(ApiReadFirewallAliasModel.class,false);
	private List<ApiReadFirewallAliasModel> listModel = new ArrayList<ApiReadFirewallAliasModel>();
	
	private ButtonTemplate btnNew = new ButtonTemplate("Add",new SvgIcon(LineAwesomeIconUrl.PLUS_SOLID));
	
	
	public FirewallAliasIpForm(ApiAliasService apiAliasService) {
		this.apiAliasService = apiAliasService;
		buildLayout();
		configComponent();
		loadData();
	}

	@Override
	public void buildLayout() {
		this.setSizeFull();
		this.add(btnNew);
		this.add(createGrid());
	}

	@Override
	public void configComponent() {
		btnNew.addClickListener(e->{
			openDialogAddNew();
		});
	}
	
	public void loadData() {
		listModel = new ArrayList<ApiReadFirewallAliasModel>();
			try {
				ApiResultPfsenseResponse<List<ApiReadFirewallAliasModel>> data = apiAliasService.readFirewallAliases();
				for(ApiReadFirewallAliasModel firewallAliasModel : data.getData()) {
					if(firewallAliasModel.getType().equals("host")||firewallAliasModel.getType().equals("network")) {
						listModel.add(firewallAliasModel);
					}
				}
			} catch (Exception e) {
				this.removeAll();
				CantConnectToPfsenseForm cantConnectToPfsenseForm = new CantConnectToPfsenseForm();
				this.add(cantConnectToPfsenseForm);
			}
			
			grid.setItems(listModel);
	}
	
	private Component createGrid() {
		grid = new Grid<ApiReadFirewallAliasModel>(ApiReadFirewallAliasModel.class,false);
		
		grid.addColumn(ApiReadFirewallAliasModel::getName).setHeader("Name").setWidth("120px").setFlexGrow(0).setResizable(true).setTooltipGenerator(ApiReadFirewallAliasModel::getName);
		grid.addComponentColumn(model->{
			Span label = new Span();
			label.setText(model.getAddress());
			label.setText(label.getText().replace(" ", "  ,  "));
			return label;
		}).setHeader("Values").setResizable(true).setTooltipGenerator(ApiReadFirewallAliasModel::getAddressText);
		grid.addColumn(ApiReadFirewallAliasModel::getDescr).setHeader("Description");
		grid.addComponentColumn(model->{
			HorizontalLayout hLayoutButton = new HorizontalLayout();
			ButtonTemplate btnEdit = new ButtonTemplate("Edit",new SvgIcon(LineAwesomeIconUrl.EDIT));
			btnEdit.addClickListener(e->{
				openDialogUpdateAliases(model);
			});
			
			ButtonTemplate btnDelete = new ButtonTemplate(new SvgIcon(LineAwesomeIconUrl.TRASH_ALT_SOLID));
			btnDelete.addThemeVariants(ButtonVariant.LUMO_ERROR);
			btnDelete.addClickListener(e->{
				openConfirmDelete(model.getName());
			});
			
			hLayoutButton.add(btnEdit,btnDelete);
			
			return hLayoutButton;
		}).setWidth("150px").setFlexGrow(0).setHeader("Thao tác");
		
		grid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
		
		return grid;
	}
	
	private void openConfirmDelete(String id) {
		ConfirmDialogTemplate confirmDialogTemplate = new ConfirmDialogTemplate("Xóa");
		confirmDialogTemplate.setText("Xác nhận xóa thông tin này");
		confirmDialogTemplate.addConfirmListener(e->{
			doDeleted(id);
		});
		
		confirmDialogTemplate.open();
		confirmDialogTemplate.setCancelable(true);
	}
	
	private void doDeleted(String id) {
		try {
			ApiResultPfsenseResponse<Object> deleted = apiAliasService.deleteFirewallAlias(id);
			if(deleted.isSuccess()) {
				NotificationTemplate.success("Đã xóa thành công");
				loadData();
			}
		} catch (Exception e) {
		}
	}
	
	private void openDialogAddNew() {
		DialogTemplate dialogTemplate = new DialogTemplate("Add new");
		FirewallAliasIpEditForm firewallAliasIpEditForm = new FirewallAliasIpEditForm(apiAliasService,null);
		
		firewallAliasIpEditForm.addChangeListener(e->{
			dialogTemplate.close();
			loadData();
			NotificationTemplate.success("Thành công");
		});
		
		dialogTemplate.getBtnSave().addClickListener(e->{
			firewallAliasIpEditForm.save();
		});
		
		dialogTemplate.add(firewallAliasIpEditForm);
		dialogTemplate.setWidth("70%");
		dialogTemplate.setHeightFull();
		dialogTemplate.open();
	}
	
	private void openDialogUpdateAliases(ApiReadFirewallAliasModel apiReadFirewallAliasModel) {
		DialogTemplate dialogTemplate = new DialogTemplate("Add new");
		FirewallAliasIpEditForm firewallAliasIpEditForm = new FirewallAliasIpEditForm(apiAliasService,apiReadFirewallAliasModel);
		
		firewallAliasIpEditForm.addChangeListener(e->{
			dialogTemplate.close();
			loadData();
			NotificationTemplate.success("Thành công");
		});
		
		dialogTemplate.getBtnSave().addClickListener(e->{
			firewallAliasIpEditForm.save();
		});
		
		dialogTemplate.add(firewallAliasIpEditForm);
		dialogTemplate.setWidth("70%");
		dialogTemplate.setHeightFull();
		dialogTemplate.open();
	}

}
