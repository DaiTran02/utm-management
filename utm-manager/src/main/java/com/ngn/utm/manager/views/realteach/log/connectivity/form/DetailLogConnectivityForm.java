package com.ngn.utm.manager.views.realteach.log.connectivity.form;

import java.util.ArrayList;
import java.util.List;

import com.ngn.utm.manager.api.ApiResultResponse;
import com.ngn.utm.manager.api.real_tech.log.connectivity.ApiDataLogConnectivityModel;
import com.ngn.utm.manager.api.real_tech.log.connectivity.ApiFilterLogConnectivityModel;
import com.ngn.utm.manager.api.real_tech.log.connectivity.ApiLogConnectivityModel;
import com.ngn.utm.manager.api.real_tech.log.connectivity.ApiLogConnectivityService;
import com.ngn.utm.manager.api.real_tech.log.connectivity.ApiLogConnectivityStaticModel;
import com.ngn.utm.manager.service.FormInterface;
import com.ngn.utm.manager.utils.LocalDateUtil;
import com.ngn.utm.manager.utils.SessionUtil;
import com.ngn.utm.manager.utils.commons.PaginationForm;
import com.ngn.utm.manager.utils.commons.VerticalLayoutTemplate;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class DetailLogConnectivityForm extends VerticalLayoutTemplate implements FormInterface{
	private static final long serialVersionUID = 1L;
	private ApiLogConnectivityService apiLogConnectivityService;
	private ApiLogConnectivityStaticModel apiLogConnectivityStaticModel;
	private ApiFilterLogConnectivityModel apiFilterLogConnectivityModel;
	
	private DateTimePicker fromDate = new DateTimePicker("Từ ngày");
	
	private DateTimePicker toDate = new DateTimePicker("Đến ngày");
	
	
	private PaginationForm paginationForm;
	
	private Grid<ApiLogConnectivityModel> grid = new Grid<ApiLogConnectivityModel>(ApiLogConnectivityModel.class,false);
	private List<ApiLogConnectivityModel> listModel = new ArrayList<ApiLogConnectivityModel>();
	
	public DetailLogConnectivityForm(ApiLogConnectivityService apiLogConnectivityService
			,ApiLogConnectivityStaticModel apiLogConnectivityStaticModel,ApiFilterLogConnectivityModel apiFilterLogConnectivityModel) {
		this.apiLogConnectivityService = apiLogConnectivityService;
		this.apiLogConnectivityStaticModel = apiLogConnectivityStaticModel;
		this.apiFilterLogConnectivityModel = apiFilterLogConnectivityModel;
		buildLayout();
		configComponent();
		loadData();
	}

	@Override
	public void buildLayout() {
		this.setSizeFull();
		HorizontalLayout hLayout = new HorizontalLayout();
		
		fromDate.setValue(LocalDateUtil.longToLocalDateTime(apiFilterLogConnectivityModel.getFromDate()));
		toDate.setValue(LocalDateUtil.longToLocalDateTime(apiFilterLogConnectivityModel.getToDate()));
		
		hLayout.add(fromDate,toDate);
		
		paginationForm = new PaginationForm(()->{
			if(paginationForm != null)
				loadData();
		});
		this.add(hLayout,paginationForm,createGrid());
	}

	@Override
	public void configComponent() {
		
	}
	
	private void loadData() {
		listModel = new ArrayList<ApiLogConnectivityModel>();
		ApiResultResponse<ApiDataLogConnectivityModel> data = apiLogConnectivityService.getLogConnectivityByFilter(getSearch());
		if(data.isSuccess()) {
			listModel.addAll(data.getData().getList());
			paginationForm.setItemCount(Integer.valueOf(String.valueOf(data.getData().getCount())));
			
			grid.setItems(listModel);
		}else {
			System.out.println(data);
		}
	}
	
	private Component createGrid() {
		grid = new Grid<ApiLogConnectivityModel>(ApiLogConnectivityModel.class,false);
		
		grid.addColumn(model->{
			return LocalDateUtil.dfDateTime.format(model.getDate());
		}).setHeader("Thời gian");
		
		grid.addComponentColumn(model->{
			HorizontalLayout hLayout = new HorizontalLayout();
			
			Span spSrc = new Span(model.getSrcIp().getIpAddress());
			
			hLayout.add(spSrc);
			
			return hLayout;
		}).setHeader("Địa chỉ nguồn");
		
		grid.addComponentColumn(model->{
			HorizontalLayout hLayoutDst = new HorizontalLayout();
			
			Span spDst = new Span(model.getDstIp().getIpAddress());
			
			hLayoutDst.add(spDst);
			
			return hLayoutDst;
		}).setHeader("Địa chỉ đích");
		
		grid.addColumn(ApiLogConnectivityModel::getDstPort).setHeader("Port đích");
		
		grid.addColumn(ApiLogConnectivityModel::getProtocol).setHeader("Protocol");
		grid.addColumn(ApiLogConnectivityModel::getAction).setHeader("Action");
		grid.addColumn(ApiLogConnectivityModel::getSourceIp).setHeader("SourceIP");
		
		return grid;
	}
	
	private ApiFilterLogConnectivityModel getSearch() {
		ApiFilterLogConnectivityModel apiFilterLogConnectivityModel = new ApiFilterLogConnectivityModel();
		
		apiFilterLogConnectivityModel.setFromDate(LocalDateUtil.localDateTimeToLong(fromDate.getValue()));
		apiFilterLogConnectivityModel.setToDate(LocalDateUtil.localDateTimeToLong(toDate.getValue()));
		
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
