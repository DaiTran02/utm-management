package com.ngn.utm.manager.views.realteach.log.login_ssh.form;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.lineawesome.LineAwesomeIconUrl;

import com.ngn.utm.manager.api.ApiResultResponse;
import com.ngn.utm.manager.api.real_tech.log.login_ssh.ApiDataLoginSSHModel;
import com.ngn.utm.manager.api.real_tech.log.login_ssh.ApiExportLoginSSHModel;
import com.ngn.utm.manager.api.real_tech.log.login_ssh.ApiFilterLoginSSHModel;
import com.ngn.utm.manager.api.real_tech.log.login_ssh.ApiLoginSSHModel;
import com.ngn.utm.manager.api.real_tech.log.login_ssh.ApiLoginSSHSerivce;
import com.ngn.utm.manager.service.FormInterface;
import com.ngn.utm.manager.utils.GeneralUtil;
import com.ngn.utm.manager.utils.LocalDateUtil;
import com.ngn.utm.manager.utils.SessionUtil;
import com.ngn.utm.manager.utils.commons.ButtonTemplate;
import com.ngn.utm.manager.utils.commons.DialogTemplate;
import com.ngn.utm.manager.utils.commons.NotificationTemplate;
import com.ngn.utm.manager.utils.commons.PaginationForm;
import com.ngn.utm.manager.utils.commons.VerticalLayoutTemplate;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.SvgIcon;

public class LoginSSHForm extends VerticalLayoutTemplate implements FormInterface{
	private static final long serialVersionUID = 1L;
	private ApiLoginSSHSerivce apiLoginSSHSerivce;
	
	private LoginSSHFilterForm loginSSHFilterForm = new LoginSSHFilterForm();
	private PaginationForm paginationForm;
	private Grid<ApiLoginSSHModel> grid = new Grid<ApiLoginSSHModel>(ApiLoginSSHModel.class,false);
	private List<ApiLoginSSHModel> listModel = new ArrayList<ApiLoginSSHModel>();
	
	public LoginSSHForm(ApiLoginSSHSerivce apiLoginSSHSerivce) {
		this.apiLoginSSHSerivce = apiLoginSSHSerivce;
		buildLayout();
		configComponent();
		loadData();
	}
	
	@Override
	public void buildLayout() {
		this.setSizeFull();
		paginationForm = new PaginationForm(()->{
			if(paginationForm != null)
				loadData();
		});
		this.add(loginSSHFilterForm,paginationForm,createGrid());
	}

	@Override
	public void configComponent() {
		loginSSHFilterForm.addChangeListener(e->{
			loadData();
		});
		
		loginSSHFilterForm.getBtnExport().addClickListener(e->{
			ApiExportLoginSSHModel apiExportLoginSSHModel = exportLogOfLoginSSH();
			if(apiExportLoginSSHModel != null) {
				loginSSHFilterForm.getBtnExport().downLoad(GeneralUtil.getStreamResource(String.format("export-%s.csv", LocalDateUtil.dfDateTime.format(System.currentTimeMillis())), 
						apiExportLoginSSHModel.getFileData()));
			}
		});
		
	}
	
	private void loadData() {
		listModel = new ArrayList<ApiLoginSSHModel>();
		ApiResultResponse<ApiDataLoginSSHModel> data = apiLoginSSHSerivce.getLogLoginSSHByFilter(getSearch());
		if(data.isSuccess()) {
			listModel.addAll(data.getData().getList());
			paginationForm.setItemCount(Integer.parseInt(String.valueOf(data.getData().getCount())));
		}else {
			NotificationTemplate.warning("Không tìm thấy dữ liệu");
		}
		grid.setItems(listModel);
	}
	
	private ApiExportLoginSSHModel exportLogOfLoginSSH() {
		ApiFilterLoginSSHModel apiFilterLoginSSHModel = getSearch();
		apiFilterLoginSSHModel.setSkip(0);
		apiFilterLoginSSHModel.setLimit(0);
		ApiResultResponse<ApiExportLoginSSHModel> export = apiLoginSSHSerivce.exportLoginSSHByFilter(apiFilterLoginSSHModel);
		if(export.isSuccess()) {
			return export.getData();
		}else {
			NotificationTemplate.warning("Không thể export");
			return null;
		}
	}
	
	private Component createGrid() {
		grid = new Grid<ApiLoginSSHModel>(ApiLoginSSHModel.class,false);
		
		grid.addColumn(model->{
			return LocalDateUtil.dfDateTime.format(model.getDate());
		}).setHeader("Thời gian").setResizable(true);
		
		grid.addColumn(ApiLoginSSHModel::getSourceName).setHeader("Nguồn").setResizable(true);
		grid.addColumn(ApiLoginSSHModel::getFromIp).setHeader("Địa chỉ IP").setResizable(true);
		grid.addColumn(ApiLoginSSHModel::getPortNumber).setHeader("Cổng").setWidth("120px").setFlexGrow(0);
		grid.addColumn(ApiLoginSSHModel::getProtocol).setHeader("Giao thức").setWidth("120px").setFlexGrow(0);
		grid.addColumn(ApiLoginSSHModel::getMethod).setHeader("Phương thức").setResizable(true);
		grid.addColumn(ApiLoginSSHModel::getResult).setHeader("Kết quả").setWidth("100px").setFlexGrow(0);
		
		grid.addComponentColumn(model->{
			ButtonTemplate btnView = new ButtonTemplate("Chi tiết",new SvgIcon(LineAwesomeIconUrl.EYE));
			btnView.addClickListener(e->{
				openDialogViewDetail(model);
			});
			
			
			return btnView;
		});
		
		return grid;
	}
	
	private void openDialogViewDetail(ApiLoginSSHModel apiLoginSSHModel) {
		DialogTemplate dialogTemplate = new DialogTemplate("Chi tiết");
		
		LoginSSHDetailForm loginSSHDetailForm = new LoginSSHDetailForm(apiLoginSSHModel);
		dialogTemplate.add(loginSSHDetailForm);
		
		dialogTemplate.open();
		dialogTemplate.setWidth("70%");
		dialogTemplate.setHeight("80%");
		dialogTemplate.getFooter().removeAll();
	}
	
	private ApiFilterLoginSSHModel getSearch() {
		ApiFilterLoginSSHModel apiFilterLoginSSHModel = loginSSHFilterForm.getSearch();
		
		apiFilterLoginSSHModel.setSkip(paginationForm.getSkip());
		apiFilterLoginSSHModel.setLimit(paginationForm.getLimit());
		apiFilterLoginSSHModel.setSourceIp(SessionUtil.getDeviceInfo().getIpAddress());
		
		return apiFilterLoginSSHModel;
	}

}
