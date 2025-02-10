package com.ngn.utm.manager.views.realteach.log.create_group.form;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.vaadin.lineawesome.LineAwesomeIconUrl;

import com.ngn.utm.manager.api.ApiResultResponse;
import com.ngn.utm.manager.api.real_tech.log.create_group.ApiDataLogCreateGroupModel;
import com.ngn.utm.manager.api.real_tech.log.create_group.ApiExportLogCreateGroupModel;
import com.ngn.utm.manager.api.real_tech.log.create_group.ApiLogCreateGroupFilterModel;
import com.ngn.utm.manager.api.real_tech.log.create_group.ApiLogCreateGroupModel;
import com.ngn.utm.manager.api.real_tech.log.create_group.ApiLogCreateGroupService;
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

public class CreateGroupForm extends VerticalLayout implements FormInterface{
	private static final long serialVersionUID = 1L;
	private ApiLogCreateGroupService apiLogCreateGroupService;
	private DateTimePicker fromDate = new DateTimePicker("Từ ngày");
	private DateTimePicker toDate = new DateTimePicker("Đến ngày");
	private ButtonTemplate btnSeach = new ButtonTemplate("Tìm",new SvgIcon(LineAwesomeIconUrl.SEARCH_SOLID));
	private ButtonTemplate btnExport = new ButtonTemplate("Xuất CSV",new SvgIcon(LineAwesomeIconUrl.DOWNLOAD_SOLID));
	private HorizontalLayout hLayoutFilter = new HorizontalLayout();
	private PaginationForm paginationForm;
	
	private Grid<ApiLogCreateGroupModel> grid = new Grid<ApiLogCreateGroupModel>(ApiLogCreateGroupModel.class,false);
	private List<ApiLogCreateGroupModel> listModel = new ArrayList<ApiLogCreateGroupModel>();
	
	public CreateGroupForm(ApiLogCreateGroupService apiLogCreateGroupService) {
		this.apiLogCreateGroupService = apiLogCreateGroupService;
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
			ApiExportLogCreateGroupModel apiExportLogCreateGroupModel = exportDataLogCreateGroup();
			if(apiExportLogCreateGroupModel != null) {
				btnExport.downLoad(GeneralUtil.getStreamResource(String.format("export-%s.csv", LocalDateUtil.dfDateTime.format(System.currentTimeMillis())), 
						apiExportLogCreateGroupModel.getFileData()));
			}
		});
	}
	
	private void loadData() {
		listModel = new ArrayList<ApiLogCreateGroupModel>();
		ApiResultResponse<ApiDataLogCreateGroupModel> data = apiLogCreateGroupService.getLogCreateGroupByFilter(getSearch());
		if(data.isSuccess()) {
			listModel.addAll(data.getData().getList());
			paginationForm.setItemCount(Integer.parseInt(String.valueOf(data.getData().getCount())));
		}else {
			NotificationTemplate.warning("Không tìm thấy dữ liệu");
		}
		
		grid.setItems(listModel);
	}
	
	private ApiExportLogCreateGroupModel exportDataLogCreateGroup() {
		ApiLogCreateGroupFilterModel aiApiFilterLogConfigChangeModel = getSearch();
		aiApiFilterLogConfigChangeModel.setLimit(0);
		aiApiFilterLogConfigChangeModel.setSkip(0);
		ApiResultResponse<ApiExportLogCreateGroupModel> data = apiLogCreateGroupService.exportLogCreateGroupByFilter(getSearch());
		if(data.isSuccess()) {
			return data.getData();
		}else {
			NotificationTemplate.warning("Không thể export");
			return null;
		}
		
	}
	
	private Component createGrid() {
		grid = new Grid<ApiLogCreateGroupModel>(ApiLogCreateGroupModel.class,false);
		
		grid.addColumn(model->{
			return LocalDateUtil.dfDateTime.format(model.getDate());
		}).setHeader("Thời gian").setResizable(true);
		grid.addColumn(ApiLogCreateGroupModel::getSourceName).setHeader("Nguồn").setResizable(true);
		grid.addColumn(ApiLogCreateGroupModel::getGroupName).setHeader("Tên nhóm").setResizable(true);
		grid.addColumn(ApiLogCreateGroupModel::getAction).setHeader("Hành động").setResizable(true);
		
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
	
	private ApiLogCreateGroupFilterModel getSearch() {
		ApiLogCreateGroupFilterModel apiLogCreateGroupFilterModel = new ApiLogCreateGroupFilterModel();
		apiLogCreateGroupFilterModel.setFromDate(LocalDateUtil.localDateTimeToLong(fromDate.getValue()));
		apiLogCreateGroupFilterModel.setToDate(LocalDateUtil.localDateTimeToLong(toDate.getValue()));
		apiLogCreateGroupFilterModel.setSourceIp(SessionUtil.getDeviceInfo().getIpAddress());
		
		return apiLogCreateGroupFilterModel;
	}

}
