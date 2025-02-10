package com.ngn.utm.manager.views.realteach.log.ips.form;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.lineawesome.LineAwesomeIconUrl;

import com.ngn.utm.manager.api.ApiResultResponse;
import com.ngn.utm.manager.api.real_tech.log.ips.ApiDataLogIpsStaticModel;
import com.ngn.utm.manager.api.real_tech.log.ips.ApiExportLogIpsModel;
import com.ngn.utm.manager.api.real_tech.log.ips.ApiFilterLogIpsModel;
import com.ngn.utm.manager.api.real_tech.log.ips.ApiLogIpsService;
import com.ngn.utm.manager.api.real_tech.log.ips.ApiLogIpsStaticModel;
import com.ngn.utm.manager.service.FormInterface;
import com.ngn.utm.manager.utils.GeneralUtil;
import com.ngn.utm.manager.utils.LocalDateUtil;
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

public class LogIpsForm extends VerticalLayoutTemplate implements FormInterface{
	private static final long serialVersionUID = 1L;
	//Service
	private ApiLogIpsService apiLogIpsService;
	
	//Components
	private LogIpsFilterForm logIpsFilterForm = new LogIpsFilterForm();
	private PaginationForm paginationForm;
	private Grid<ApiLogIpsStaticModel> grid = new Grid<ApiLogIpsStaticModel>(ApiLogIpsStaticModel.class,false);
	private List<ApiLogIpsStaticModel> listModel = new ArrayList<ApiLogIpsStaticModel>();
	
	public LogIpsForm(ApiLogIpsService apiLogIpsService) {
		this.apiLogIpsService = apiLogIpsService;
		buildLayout();
		configComponent();
	}

	@Override
	public void buildLayout() {
		this.setSizeFull();
		paginationForm = new PaginationForm(()->{
			if(paginationForm != null)
				loadData();
		});
		this.add(logIpsFilterForm,paginationForm,createGrid());
	}

	@Override
	public void configComponent() {
		logIpsFilterForm.addChangeListener(e->{
			loadData();
		});
		
		logIpsFilterForm.getBtnExport().addClickListener(e->{
			ApiExportLogIpsModel exportLogIpsModel = exportIpsStatictis();
			if(exportLogIpsModel != null) {
				try {
					logIpsFilterForm.getBtnExport().downLoad(GeneralUtil.getStreamResource(String.format("export-%s.csv", LocalDateUtil.dfDateTime.format(System.currentTimeMillis())), 
							exportLogIpsModel.getFileData()));
				} catch (Exception e2) {
				}
			}
		});
		
	}
	
	public void loadData() {
		listModel = new ArrayList<ApiLogIpsStaticModel>();
		ApiResultResponse<ApiDataLogIpsStaticModel> data = apiLogIpsService.getIpsStatictisByFilter(getSearch());
		if(data.isSuccess()) {
			listModel.addAll(data.getData().getList());
			paginationForm.setItemCount(Integer.parseInt(String.valueOf(data.getData().getCount())));
			grid.setItems(listModel);
		}else {
			NotificationTemplate.warning("Không tìm thấy dữ liệu");
		}
	}
	
	private ApiExportLogIpsModel exportIpsStatictis() {
		ApiResultResponse<ApiExportLogIpsModel> data = apiLogIpsService.exportIpsStatictisByFilter(getSearch());
		if(data.isSuccess()) {
			return data.getData();
		}
		return null;
	}
	
	private Component createGrid() {
		grid = new Grid<ApiLogIpsStaticModel>(ApiLogIpsStaticModel.class,false);
		grid.addColumn(ApiLogIpsStaticModel::getSrcIp).setHeader("Máy chủ/thiết bị").setResizable(true);
		grid.addColumn(ApiLogIpsStaticModel::getDstIp).setHeader("Địa chỉ đích").setResizable(true);
		grid.addColumn(ApiLogIpsStaticModel::getDstPort).setHeader("Cổng đích").setResizable(true);
		grid.addColumn(ApiLogIpsStaticModel::getProtocol).setHeader("Giao thức").setResizable(true);
		grid.addColumn(ApiLogIpsStaticModel::getSigName).setHeader("Tên mẫu nhận dạng").setResizable(true);

		grid.addComponentColumn(model->{
			return new Span(model.getCount()+"");
		}).setHeader("Số lượng").setResizable(true);
		
		grid.addComponentColumn(model->{
			HorizontalLayout hLayoutButton = new HorizontalLayout();
			
			ButtonTemplate btnView = new ButtonTemplate("Chi tiết",new SvgIcon(LineAwesomeIconUrl.EYE));
			btnView.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
			btnView.addThemeVariants(ButtonVariant.LUMO_SMALL);
			btnView.addClickListener(e->{
				openDialogDetail(model);
			});
			
			ButtonTemplate btnExport = new ButtonTemplate("Xuất CSV",new SvgIcon(LineAwesomeIconUrl.FILE_EXCEL));
			btnExport.addThemeVariants(ButtonVariant.LUMO_SMALL);
			btnExport.setDownload();
			btnExport.addClickListener(e->{
				ApiExportLogIpsModel apiExportLogIpsModel = exportLogIp(model);
				if(apiExportLogIpsModel != null) {
					try {
						btnExport.downLoad(GeneralUtil.getStreamResource(String.format("export-%s.csv", LocalDateUtil.dfDateTime.format(System.currentTimeMillis())),
								apiExportLogIpsModel.getFileData()));
					} catch (Exception e2) {
					}
				}
			});
			
			hLayoutButton.add(btnView,btnExport,btnExport.getAnchor());
			
			return hLayoutButton;
		}).setWidth("220px").setFlexGrow(0).setHeader("Thao tác");
		
		return grid;
	}
	
	private void openDialogDetail(ApiLogIpsStaticModel apiLogIpsStaticModel) {
		DialogTemplate dialogTemplate = new DialogTemplate("Chi tiết");
		
		DetailLogIpsForm detailLogIpsForm = new DetailLogIpsForm(apiLogIpsService, apiLogIpsStaticModel, getSearch());
		dialogTemplate.add(detailLogIpsForm);
		dialogTemplate.setSizeFull();
		dialogTemplate.open();
		dialogTemplate.getFooter().removeAll();
	}
	
	private ApiExportLogIpsModel exportLogIp(ApiLogIpsStaticModel apiLogIpsStaticModel) {
		ApiFilterLogIpsModel apiFilterLogIpsModel = new ApiFilterLogIpsModel();
		apiFilterLogIpsModel.setSkip(0);
		apiFilterLogIpsModel.setLimit(0);
		apiFilterLogIpsModel.setSrcIp(apiLogIpsStaticModel.getSrcIp());
		apiFilterLogIpsModel.setDstIp(apiLogIpsStaticModel.getDstIp());
		apiFilterLogIpsModel.setDstPort(String.valueOf(apiLogIpsStaticModel.getDstPort()));
		apiFilterLogIpsModel.setProtocol(apiLogIpsStaticModel.getProtocol());
		apiFilterLogIpsModel.setSigName(apiLogIpsStaticModel.getSigName());
		apiFilterLogIpsModel.setFromDate(getSearch().getFromDate());
		apiFilterLogIpsModel.setToDate(getSearch().getToDate());
		
		ApiResultResponse<ApiExportLogIpsModel> data = apiLogIpsService.exportIpsByFilter(apiFilterLogIpsModel);
		if(data.isSuccess()) {
			return data.getData();
		}else {
			NotificationTemplate.warning("Không tìm thấy dữ liệu");
			return null;
		}
	}
	
	private ApiFilterLogIpsModel getSearch() {
		ApiFilterLogIpsModel apiFilterLogIpsModel = logIpsFilterForm.getSearch();
		apiFilterLogIpsModel.setSkip(paginationForm.getSkip());
		apiFilterLogIpsModel.setLimit(paginationForm.getLimit());
//		apiFilterLogIpsModel.setSourceIp(SessionUtil.getDeviceInfo().getIpAddress());
		
		return apiFilterLogIpsModel;
	}

}
