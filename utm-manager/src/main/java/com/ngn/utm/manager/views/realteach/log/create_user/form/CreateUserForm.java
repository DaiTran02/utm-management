package com.ngn.utm.manager.views.realteach.log.create_user.form;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.vaadin.lineawesome.LineAwesomeIconUrl;

import com.ngn.utm.manager.api.ApiResultResponse;
import com.ngn.utm.manager.api.real_tech.log.create_user.ApiCreateUserService;
import com.ngn.utm.manager.api.real_tech.log.create_user.ApiDataLogCreateUserModel;
import com.ngn.utm.manager.api.real_tech.log.create_user.ApiExportLogCreateUserModel;
import com.ngn.utm.manager.api.real_tech.log.create_user.ApiFilterLogCreateUserModel;
import com.ngn.utm.manager.api.real_tech.log.create_user.ApiLogCreateUserModel;
import com.ngn.utm.manager.service.FormInterface;
import com.ngn.utm.manager.utils.GeneralUtil;
import com.ngn.utm.manager.utils.LocalDateUtil;
import com.ngn.utm.manager.utils.SessionUtil;
import com.ngn.utm.manager.utils.commons.ButtonTemplate;
import com.ngn.utm.manager.utils.commons.NotificationTemplate;
import com.ngn.utm.manager.utils.commons.PaginationForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.datetimepicker.DateTimePickerVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class CreateUserForm extends VerticalLayout implements FormInterface{
	private static final long serialVersionUID = 1L;
	private ApiCreateUserService apiCreateUserService;
	private DateTimePicker fromDate = new DateTimePicker("Từ ngày");
	private DateTimePicker toDate = new DateTimePicker("Đến ngày");
	private ButtonTemplate btnSeach = new ButtonTemplate("Tìm",new SvgIcon(LineAwesomeIconUrl.SEARCH_SOLID));
	private ButtonTemplate btnExport = new ButtonTemplate("Xuất CSV",new SvgIcon(LineAwesomeIconUrl.DOWNLOAD_SOLID));
	private HorizontalLayout hLayoutFilter = new HorizontalLayout();
	private PaginationForm paginationForm;
	
	private Grid<ApiLogCreateUserModel> grid = new Grid<ApiLogCreateUserModel>(ApiLogCreateUserModel.class,false);
	private List<ApiLogCreateUserModel> listModel = new ArrayList<ApiLogCreateUserModel>();

	public CreateUserForm(ApiCreateUserService apiCreateUserService) {
		this.apiCreateUserService = apiCreateUserService;
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
		
		this.add(hLayoutFilter,paginationForm,createGrid());
		createLayoutFilter();
	}

	@Override
	public void configComponent() {
		btnSeach.addClickListener(e->{
			loadData();
		});
		
		btnExport.addClickListener(e->{
			ApiExportLogCreateUserModel apiExportLogCreateUserModel = exportDataLogCreateUser();
			if(apiExportLogCreateUserModel != null) {
				btnExport.downLoad(GeneralUtil.getStreamResource(String.format("export-%s.csv", LocalDateUtil.dfDateTime.format(System.currentTimeMillis())), 
						apiExportLogCreateUserModel.getFileData()));
			}
		});
	}
	
	private void loadData() {
		listModel = new ArrayList<ApiLogCreateUserModel>();
		ApiResultResponse<ApiDataLogCreateUserModel> data = apiCreateUserService.getDataLogCreateUserByFilter(getSearch());
		if(data.isSuccess()) {
			listModel.addAll(data.getData().getList());
			paginationForm.setItemCount(Integer.parseInt(String.valueOf(data.getData().getCount())));
		}else {
			NotificationTemplate.warning("Không tìm thấy thông tin");
		}
		
		grid.setItems(listModel);
		
	}
	
	private ApiExportLogCreateUserModel exportDataLogCreateUser() {
		ApiFilterLogCreateUserModel apiFilterCreateUser = getSearch();
		apiFilterCreateUser.setLimit(0);
		apiFilterCreateUser.setSkip(0);
		ApiResultResponse<ApiExportLogCreateUserModel> data = apiCreateUserService.exportLogCreateUserByFilter(apiFilterCreateUser);
		if(data.isSuccess()) {
			return data.getData();
		}else {
			NotificationTemplate.warning("Không thể export");
			return null;
		}
	}
	
	private Component createGrid() {
		grid = new Grid<ApiLogCreateUserModel>(ApiLogCreateUserModel.class,false);
		
		grid.addColumn(model->{
			return LocalDateUtil.dfDateTime.format(model.getDate());
		}).setHeader("Thời gian").setResizable(true);
		grid.addColumn(ApiLogCreateUserModel::getSourceName).setHeader("Nguồn").setResizable(true);
		grid.addColumn(ApiLogCreateUserModel::getUserName).setHeader("Tài khoản").setResizable(true);
		grid.addColumn(ApiLogCreateUserModel::getAction).setHeader("Hành động").setResizable(true);
		
		return grid;
	}
	
	private void createLayoutFilter() {
		fromDate.addThemeVariants(DateTimePickerVariant.LUMO_SMALL);
		fromDate.setValue(LocalDateTime.now().minusMonths(1));
		fromDate.setLocale(LocalDateUtil.localeVietNam());
		
		toDate.addThemeVariants(DateTimePickerVariant.LUMO_SMALL);
		toDate.setValue(LocalDateTime.now());
		toDate.setLocale(LocalDateUtil.localeVietNam());
		
		btnSeach.addThemeVariants(ButtonVariant.LUMO_SMALL);
		btnSeach.getStyle().setMarginTop("26px");
		
		btnExport.addThemeVariants(ButtonVariant.LUMO_SMALL);
		btnExport.getStyle().setMarginTop("26px");
		btnExport.setDownload();
		
		hLayoutFilter.removeAll();
		hLayoutFilter.add(fromDate,toDate,btnSeach,btnExport,btnExport.getAnchor());
	}
	
	private ApiFilterLogCreateUserModel getSearch() {
		ApiFilterLogCreateUserModel apiLogCreateUserFilterModel = new ApiFilterLogCreateUserModel();
		apiLogCreateUserFilterModel.setFromDate(LocalDateUtil.localDateTimeToLong(fromDate.getValue()));
		apiLogCreateUserFilterModel.setToDate(LocalDateUtil.localDateTimeToLong(toDate.getValue()));
		apiLogCreateUserFilterModel.setSourceIp(SessionUtil.getDeviceInfo().getIpAddress());
		
		return apiLogCreateUserFilterModel;
	}

}
