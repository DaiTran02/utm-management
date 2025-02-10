package com.ngn.utm.manager.views.realteach.log.config_change.form;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.lineawesome.LineAwesomeIconUrl;

import com.ngn.utm.manager.api.ApiResultResponse;
import com.ngn.utm.manager.api.real_tech.log.config_change.ApiDataLogConfigChangeModel;
import com.ngn.utm.manager.api.real_tech.log.config_change.ApiExportLogConfigChangeModel;
import com.ngn.utm.manager.api.real_tech.log.config_change.ApiFilterLogConfigChangeModel;
import com.ngn.utm.manager.api.real_tech.log.config_change.ApiLogConfigChangeModel;
import com.ngn.utm.manager.api.real_tech.log.config_change.ApiLogConfigChangeService;
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

public class LogConfigChangeForm extends VerticalLayoutTemplate implements FormInterface{
	private static final long serialVersionUID = 1L;
	private ApiLogConfigChangeService apiLogConfigChangeService;
	
	private LogConfigChangeFilterForm logConfigChangeFilterForm = new LogConfigChangeFilterForm();
	private PaginationForm paginationForm;
	private Grid<ApiLogConfigChangeModel> grid = new Grid<ApiLogConfigChangeModel>(ApiLogConfigChangeModel.class,false);
	private List<ApiLogConfigChangeModel> listModel = new ArrayList<ApiLogConfigChangeModel>();
	
	public LogConfigChangeForm(ApiLogConfigChangeService apiLogConfigChangeService) {
		this.apiLogConfigChangeService = apiLogConfigChangeService;
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
		this.add(logConfigChangeFilterForm,paginationForm,createGrid());
	}

	@Override
	public void configComponent() {
		logConfigChangeFilterForm.addChangeListener(e->{
			loadData();
		});
		
		logConfigChangeFilterForm.getBtnExport().addClickListener(e->{
			ApiExportLogConfigChangeModel apiExportLogConfigChangeModel = exportLogConfigChange();
			if(apiExportLogConfigChangeModel != null) {
				try {
					logConfigChangeFilterForm
					.getBtnExport()
						.downLoad(GeneralUtil.getStreamResource(String.format("export-%s.csv", LocalDateUtil.dfDateTime.format(System.currentTimeMillis())), 
								apiExportLogConfigChangeModel.getFileData()));
				} catch (Exception e2) {
				}
			}else {
				NotificationTemplate.warning("Không thể export");
			}
		});
		
	}
	
	private void loadData() {
		listModel = new ArrayList<ApiLogConfigChangeModel>();
		ApiResultResponse<ApiDataLogConfigChangeModel> data = apiLogConfigChangeService.getLogConfigChangeByFilter(getSearch());
		if(data.isSuccess()) {
			listModel.addAll(data.getData().getList());
			paginationForm.setItemCount(Integer.parseInt(String.valueOf(data.getData().getCount())));
		}else {
			NotificationTemplate.warning("Không tìm thấy dữ liệu");
		}
		grid.setItems(listModel);
	}
	
	private ApiExportLogConfigChangeModel exportLogConfigChange() {
		ApiFilterLogConfigChangeModel apiFilterLogConfigChangeModel = getSearch();
		apiFilterLogConfigChangeModel.setSkip(0);
		apiFilterLogConfigChangeModel.setLimit(0);
		ApiResultResponse<ApiExportLogConfigChangeModel> data = apiLogConfigChangeService.exportLogConfigChange(getSearch());
		if(data.isSuccess()) {
			return data.getData();
		}
		
		return null;
	}
	
	private Component createGrid() {
		grid = new Grid<ApiLogConfigChangeModel>(ApiLogConfigChangeModel.class,false);
		
		grid.addColumn(model->{
			return LocalDateUtil.dfDateTime.format(model.getDate());
		}).setHeader("Thời gian").setResizable(true).setWidth("145px").setFlexGrow(0);
		grid.addColumn(ApiLogConfigChangeModel::getSourceName).setHeader("Nguồn").setResizable(true).setWidth("145px").setFlexGrow(0);
		grid.addColumn(ApiLogConfigChangeModel::getUserName).setHeader("Tài khoản").setResizable(true).setWidth("110px").setFlexGrow(0);
		grid.addColumn(ApiLogConfigChangeModel::getReason).setHeader("Trạng thái").setResizable(true).setTooltipGenerator(ApiLogConfigChangeModel::getReason);
		grid.addColumn(ApiLogConfigChangeModel::getPath).setHeader("Đường dẫn").setResizable(true).setTooltipGenerator(ApiLogConfigChangeModel::getPath);
		grid.addColumn(ApiLogConfigChangeModel::getIpAddress).setHeader("IP").setResizable(true).setWidth("110px").setFlexGrow(0);
		grid.addColumn(ApiLogConfigChangeModel::getDatabase).setHeader("Cơ sở dữ liệu").setResizable(true);
		
		grid.addComponentColumn(model->{
			ButtonTemplate btnView = new ButtonTemplate("Chi tiết",new SvgIcon(LineAwesomeIconUrl.EYE));
			
			btnView.addClickListener(e->{
				openDialogViewDetail(model);
			});
			
			return btnView;
		}).setHeader("Thao tác");
		
		return grid;
	}
	
	private void openDialogViewDetail(ApiLogConfigChangeModel apiLogConfigChangeModel) {
		DialogTemplate dialogTemplate = new DialogTemplate("Chi tiết");

		DetailLogConfigChangeForm detailLogConfigChangeForm = new DetailLogConfigChangeForm(apiLogConfigChangeModel);
		
		dialogTemplate.add(detailLogConfigChangeForm);
		
		dialogTemplate.getFooter().removeAll();
		
		dialogTemplate.setHeight("60%");
		dialogTemplate.setWidth("70%");
		
		dialogTemplate.open();
	}
	
	private ApiFilterLogConfigChangeModel getSearch() {
		ApiFilterLogConfigChangeModel apiFilterLogConfigChangeModel = logConfigChangeFilterForm.getSearch();
		
		apiFilterLogConfigChangeModel.setSourceIp(SessionUtil.getDeviceInfo().getIpAddress());
		apiFilterLogConfigChangeModel.setSkip(paginationForm.getSkip());
		apiFilterLogConfigChangeModel.setLimit(paginationForm.getLimit());
		
		System.out.println(apiFilterLogConfigChangeModel);
		
		return apiFilterLogConfigChangeModel;
	}

}
