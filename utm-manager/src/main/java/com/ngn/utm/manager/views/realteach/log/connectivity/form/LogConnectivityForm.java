package com.ngn.utm.manager.views.realteach.log.connectivity.form;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.lineawesome.LineAwesomeIconUrl;

import com.ngn.utm.manager.api.ApiResultResponse;
import com.ngn.utm.manager.api.real_tech.log.connectivity.ApiDataLogConnectiviStaticModel;
import com.ngn.utm.manager.api.real_tech.log.connectivity.ApiExportLogConnectivityModel;
import com.ngn.utm.manager.api.real_tech.log.connectivity.ApiFilterLogConnectivityModel;
import com.ngn.utm.manager.api.real_tech.log.connectivity.ApiLogConnectivityService;
import com.ngn.utm.manager.api.real_tech.log.connectivity.ApiLogConnectivityStaticModel;
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
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class LogConnectivityForm extends VerticalLayoutTemplate implements FormInterface{
	private static final long serialVersionUID = 1L;
	private ApiLogConnectivityService apiLogConnectivityService;
	
	private FilterLogConnectivityForm filterLogConnectivityForm = new FilterLogConnectivityForm();
	private PaginationForm paginationForm;
	
	private Grid<ApiLogConnectivityStaticModel> grid = new Grid<ApiLogConnectivityStaticModel>(ApiLogConnectivityStaticModel.class,false);
	private List<ApiLogConnectivityStaticModel> listModel = new ArrayList<ApiLogConnectivityStaticModel>();
	private ApiExportLogConnectivityModel exportLogConnectivityModel = new ApiExportLogConnectivityModel();
	
	public LogConnectivityForm(ApiLogConnectivityService apiLogConnectivityService) {
		this.apiLogConnectivityService = apiLogConnectivityService;
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
		
		this.add(filterLogConnectivityForm,paginationForm,createGrid());
	}

	@Override
	public void configComponent() {
		filterLogConnectivityForm.addChangeListener(e->{
			loadData();
		});
		
		filterLogConnectivityForm.getBtnExport().addClickListener(e->{
			exportData();
			try {
				filterLogConnectivityForm
					.getBtnExport()
						.downLoad(GeneralUtil.getStreamResource(String.format("export-%s.csv", LocalDateUtil.dfDateTime.format(System.currentTimeMillis())), 
								exportLogConnectivityModel.getFileData()));
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		});
		
	}
	
	private void loadData() {
		listModel = new ArrayList<ApiLogConnectivityStaticModel>();
		ApiResultResponse<ApiDataLogConnectiviStaticModel> data = apiLogConnectivityService.getLogConnectivityStaticByFilter(getSearch());
		if(data.isSuccess()) {
			listModel.addAll(data.getData().getList());
			
			paginationForm.setItemCount(Integer.parseInt(String.valueOf(data.getData().getCount())));
			
			grid.setItems(listModel);
		}
	}
	
	private void exportData() {
		ApiResultResponse<ApiExportLogConnectivityModel> export = apiLogConnectivityService.exportLogConnectivityStaticByFilter(getSearch());
		if(export.isSuccess()) {
			exportLogConnectivityModel = new ApiExportLogConnectivityModel();
			exportLogConnectivityModel = export.getData();
		}
	}
	
	private Component createGrid() {
		grid = new Grid<ApiLogConnectivityStaticModel>(ApiLogConnectivityStaticModel.class,false);
		
		grid.addColumn(ApiLogConnectivityStaticModel::getSrcIp).setHeader("Máy chủ/thiết bị").setResizable(true);
		grid.addColumn(ApiLogConnectivityStaticModel::getDstIp).setHeader("Địa chỉ đích").setResizable(true);
		grid.addColumn(ApiLogConnectivityStaticModel::getDstPort).setHeader("Cổng đích").setResizable(true);
		grid.addColumn(ApiLogConnectivityStaticModel::getProtocol).setHeader("Giao thức").setResizable(true);
		grid.addColumn(ApiLogConnectivityStaticModel::getAction).setHeader("Hành động").setResizable(true);

		grid.addComponentColumn(model->{
			return new Span(model.getCount()+"");
		}).setHeader("Số lượng").setResizable(true);
		
		grid.addComponentColumn(model->{
			HorizontalLayout hLayoutButton = new HorizontalLayout();
			
			ButtonTemplate btnView = new ButtonTemplate("Chi tiết",new SvgIcon(LineAwesomeIconUrl.EYE));
			btnView.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
			btnView.addThemeVariants(ButtonVariant.LUMO_SMALL);
			btnView.addClickListener(e->{
				openDialogViewDetail(model);
			});
			
			ButtonTemplate btnExport = new ButtonTemplate("Xuất CSV",new SvgIcon(LineAwesomeIconUrl.FILE_EXCEL));
			btnExport.addThemeVariants(ButtonVariant.LUMO_SMALL);
			btnExport.setDownload();
			btnExport.addClickListener(e->{
				ApiExportLogConnectivityModel exportLogConnectivityModel = exportDataLogConnectivity(model);
				if(exportLogConnectivityModel == null) {
					NotificationTemplate.success("Không thể export");
				}else {
					btnExport.downLoad(GeneralUtil.getStreamResource(String.format("export-%s.csv", LocalDateUtil.dfDateTime.format(System.currentTimeMillis())),
							exportLogConnectivityModel.getFileData()));
				}
			});
			
			hLayoutButton.add(btnView,btnExport,btnExport.getAnchor());
			
			return hLayoutButton;
		}).setWidth("220px").setFlexGrow(0).setHeader("Thao tác");
		
		
		return grid;
	}
	
	private void openDialogViewDetail(ApiLogConnectivityStaticModel apiLogConnectivityStaticModel) {
		DialogTemplate dialogTemplate = new DialogTemplate("Chi tiết");
		
		DetailLogConnectivityForm detailLogConnectivityForm = new DetailLogConnectivityForm(apiLogConnectivityService, apiLogConnectivityStaticModel,getSearch());
		
		dialogTemplate.add(detailLogConnectivityForm);
		dialogTemplate.open();
		dialogTemplate.setSizeFull();
		dialogTemplate.getFooter().removeAll();
	}
	
	private ApiFilterLogConnectivityModel getSearch() {
		ApiFilterLogConnectivityModel apiFilterLogConnectivityModel = filterLogConnectivityForm.getSearch();
		apiFilterLogConnectivityModel.setSourceIp(SessionUtil.getDeviceInfo().getIpAddress());
		apiFilterLogConnectivityModel.setSkip(paginationForm.getSkip());
		apiFilterLogConnectivityModel.setLimit(paginationForm.getLimit());
		
		
		return apiFilterLogConnectivityModel;
	}
	
	private ApiExportLogConnectivityModel exportDataLogConnectivity(ApiLogConnectivityStaticModel apiLogConnectivityStaticModel) {
		ApiResultResponse<ApiExportLogConnectivityModel> data = apiLogConnectivityService.exportLogConnectivityByFilter(getSearchExportLogConnectivity(apiLogConnectivityStaticModel));
		if(data.isSuccess()) {
			return data.getData();
		}
		
		return null;
	}
	
	
	private ApiFilterLogConnectivityModel getSearchExportLogConnectivity(ApiLogConnectivityStaticModel apiLogConnectivityStaticModel) {
		ApiFilterLogConnectivityModel apiFilterLogConnectivityModel = new ApiFilterLogConnectivityModel();
		
		apiFilterLogConnectivityModel.setFromDate(getSearch().getFromDate());
		apiFilterLogConnectivityModel.setToDate(getSearch().getToDate());
		
		apiFilterLogConnectivityModel.setSkip(paginationForm.getSkip());
		apiFilterLogConnectivityModel.setLimit(paginationForm.getLimit());
		
		apiFilterLogConnectivityModel.setSourceIp(SessionUtil.getDeviceInfo().getIpAddress());
		apiFilterLogConnectivityModel.setSrcIp(apiLogConnectivityStaticModel.getSrcIp());
		apiFilterLogConnectivityModel.setDstIp(apiLogConnectivityStaticModel.getDstIp());
		apiFilterLogConnectivityModel.setDstPort(String.valueOf(apiLogConnectivityStaticModel.getDstPort()));
		apiFilterLogConnectivityModel.setProtocol(apiLogConnectivityStaticModel.getProtocol());
		apiFilterLogConnectivityModel.setAction(apiLogConnectivityStaticModel.getAction());
		
		return apiFilterLogConnectivityModel;
	}

}
