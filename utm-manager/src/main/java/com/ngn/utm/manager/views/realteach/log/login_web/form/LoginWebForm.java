package com.ngn.utm.manager.views.realteach.log.login_web.form;

import java.util.ArrayList;
import java.util.List;

import com.ngn.utm.manager.api.ApiResultResponse;
import com.ngn.utm.manager.api.real_tech.log.login_web.ApiDataLoginWebModel;
import com.ngn.utm.manager.api.real_tech.log.login_web.ApiExportLoginWebModel;
import com.ngn.utm.manager.api.real_tech.log.login_web.ApiFilterLoginWebModel;
import com.ngn.utm.manager.api.real_tech.log.login_web.ApiLoginWebModel;
import com.ngn.utm.manager.api.real_tech.log.login_web.ApiLoginWebService;
import com.ngn.utm.manager.service.FormInterface;
import com.ngn.utm.manager.utils.GeneralUtil;
import com.ngn.utm.manager.utils.LocalDateUtil;
import com.ngn.utm.manager.utils.SessionUtil;
import com.ngn.utm.manager.utils.commons.NotificationTemplate;
import com.ngn.utm.manager.utils.commons.PaginationForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class LoginWebForm extends VerticalLayout implements FormInterface{
	private static final long serialVersionUID = 1L;
	private ApiLoginWebService apiLoginWebService;
	
	private LoginWebFilterForm loginWebFilterForm = new LoginWebFilterForm();
	private PaginationForm paginationForm;
	
	private Grid<ApiLoginWebModel> grid = new Grid<ApiLoginWebModel>(ApiLoginWebModel.class,false);
	private List<ApiLoginWebModel> listModel = new ArrayList<ApiLoginWebModel>();
	
	public LoginWebForm(ApiLoginWebService apiLoginWebService) {
		this.apiLoginWebService = apiLoginWebService;
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
		
		this.add(loginWebFilterForm,paginationForm,createGrid());
	}

	@Override
	public void configComponent() {
		loginWebFilterForm.addChangeListener(e->{
			loadData();
		});
		
		loginWebFilterForm.getBtnExport().addClickListener(e->{
			ApiExportLoginWebModel apiExportLoginWebModel = exportLogOfLoginWeb();
			if(apiExportLoginWebModel != null) {
				loginWebFilterForm.getBtnExport().downLoad(GeneralUtil.getStreamResource(String.format("export-%s.csv", LocalDateUtil.dfDateTime.format(System.currentTimeMillis())), 
						apiExportLoginWebModel.getFileData()));
			}
		});
		
	}
	
	private void loadData() {
		listModel = new ArrayList<ApiLoginWebModel>();
		ApiResultResponse<ApiDataLoginWebModel> data = apiLoginWebService.getLogLoginWebByFilter(getSearch());
		if(data.isSuccess()) {
			listModel.addAll(data.getData().getList());
			paginationForm.setItemCount(Integer.parseInt(String.valueOf(data.getData().getCount())));
		}else {
			NotificationTemplate.warning("Không tìm thấy dữ liệu");
		}
		
		grid.setItems(listModel);
	}
	
	private ApiExportLoginWebModel exportLogOfLoginWeb() {
		ApiFilterLoginWebModel apiFilterLoginWebModel = getSearch();
		apiFilterLoginWebModel.setSkip(0);
		apiFilterLoginWebModel.setLimit(0);
		ApiResultResponse<ApiExportLoginWebModel> data = apiLoginWebService.exportLoginWebByFilter(apiFilterLoginWebModel);
		if(data.isSuccess()) {
			return data.getData();
		}else {
			NotificationTemplate.warning("Không thể export");
			return null;
		}
	}
	
	private Component createGrid() {
		grid = new Grid<ApiLoginWebModel>(ApiLoginWebModel.class,false);
		
		grid.addColumn(model->{
			return LocalDateUtil.dfDateTime.format(model.getDate());
		}).setHeader("Thời gian").setResizable(true);
		
		grid.addColumn(ApiLoginWebModel::getSourceName).setHeader("Nguồn").setResizable(true);
		grid.addColumn(ApiLoginWebModel::getUserName).setHeader("Tài khoản").setWidth("130px").setFlexGrow(0);
		grid.addColumn(ApiLoginWebModel::getIpAddress).setHeader("Địa chỉ IP").setResizable(true);
		grid.addColumn(ApiLoginWebModel::getPath).setHeader("Đường dẫn").setResizable(true);
		grid.addColumn(ApiLoginWebModel::getDatabase).setHeader("Cơ sở dữ liệu").setResizable(true);
		grid.addColumn(ApiLoginWebModel::getResult).setHeader("Kết quả").setWidth("100px").setFlexGrow(0);
		
		return grid;
	}
	
	private ApiFilterLoginWebModel getSearch() {
		ApiFilterLoginWebModel apiFilterLoginWebModel = loginWebFilterForm.getSearch();
		
		apiFilterLoginWebModel.setSourceIp(SessionUtil.getDeviceInfo().getIpAddress());
		apiFilterLoginWebModel.setSkip(paginationForm.getSkip());
		apiFilterLoginWebModel.setLimit(paginationForm.getLimit());
		
		return apiFilterLoginWebModel;
	}

}
