package com.ngn.utm.manager.views.realteach.log.ips.form;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.lineawesome.LineAwesomeIconUrl;

import com.ngn.utm.manager.api.ApiResultResponse;
import com.ngn.utm.manager.api.real_tech.log.ips.ApiDataLogIpsModel;
import com.ngn.utm.manager.api.real_tech.log.ips.ApiFilterLogIpsModel;
import com.ngn.utm.manager.api.real_tech.log.ips.ApiLogIpsModel;
import com.ngn.utm.manager.api.real_tech.log.ips.ApiLogIpsService;
import com.ngn.utm.manager.api.real_tech.log.ips.ApiLogIpsStaticModel;
import com.ngn.utm.manager.service.FormInterface;
import com.ngn.utm.manager.utils.LocalDateUtil;
import com.ngn.utm.manager.utils.commons.ButtonTemplate;
import com.ngn.utm.manager.utils.commons.NotificationTemplate;
import com.ngn.utm.manager.utils.commons.PaginationForm;
import com.ngn.utm.manager.utils.commons.VerticalLayoutTemplate;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.datetimepicker.DateTimePickerVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;

public class DetailLogIpsForm extends VerticalLayoutTemplate implements FormInterface{
	private static final long serialVersionUID = 1L;
	//Service
	private ApiLogIpsService apiLogIpsService;
	private ApiLogIpsStaticModel apiLogIpsStaticModel;
	private ApiFilterLogIpsModel apiFilterLogIpsStaticModel;
	
	private DateTimePicker fromDate = new DateTimePicker("Từ ngày");
	private DateTimePicker toDate = new DateTimePicker("Đến ngày");
	private TextField txtSearch = new TextField("Tìm tên mẫu nhận dạng");
	private ButtonTemplate btnSearch = new ButtonTemplate("Tìm",new SvgIcon(LineAwesomeIconUrl.SEARCH_SOLID));
	private PaginationForm paginationForm;
	
	private Grid<ApiLogIpsModel> grid = new Grid<ApiLogIpsModel>(ApiLogIpsModel.class,false);
	private List<ApiLogIpsModel> listModel = new ArrayList<ApiLogIpsModel>();
	
	public DetailLogIpsForm(ApiLogIpsService apiLogIpsService,ApiLogIpsStaticModel apiLogIpsStaticModel,ApiFilterLogIpsModel apiFilterLogIpsStaticModel) {
		this.apiLogIpsService = apiLogIpsService;
		this.apiLogIpsStaticModel = apiLogIpsStaticModel;
		this.apiFilterLogIpsStaticModel = apiFilterLogIpsStaticModel;
		buildLayout();
		configComponent();
		loadData();
	}
	
	@Override
	public void buildLayout() {
		this.setSizeFull();
		HorizontalLayout hlayout = new HorizontalLayout();
		hlayout.add(fromDate,toDate,txtSearch,btnSearch);
		hlayout.expand(txtSearch);
		hlayout.setWidthFull();
		
		txtSearch.addThemeVariants(TextFieldVariant.LUMO_SMALL);
		btnSearch.addThemeVariants(ButtonVariant.LUMO_SMALL);
		btnSearch.getStyle().setMarginTop("27px");
		
		fromDate.addThemeVariants(DateTimePickerVariant.LUMO_SMALL);
		fromDate.setValue(LocalDateUtil.longToLocalDateTime(apiFilterLogIpsStaticModel.getFromDate()));
		fromDate.setLocale(LocalDateUtil.localeVietNam());
		
		toDate.addThemeVariants(DateTimePickerVariant.LUMO_SMALL);
		toDate.setValue(LocalDateUtil.longToLocalDateTime(apiFilterLogIpsStaticModel.getToDate()));
		toDate.setLocale(LocalDateUtil.localeVietNam());
		
		paginationForm = new PaginationForm(()->{
			if(paginationForm != null)
				loadData();
		});
		
		this.add(hlayout,paginationForm,createGrid());
	}

	@Override
	public void configComponent() {
		btnSearch.addClickListener(e->{
			loadData();
		});
	}
	
	private void loadData() {
		listModel = new ArrayList<ApiLogIpsModel>();
		ApiResultResponse<ApiDataLogIpsModel> data = apiLogIpsService.getIpsByFilter(getSearch());
		if(data.isSuccess()) {
			listModel.addAll(data.getData().getList());
			paginationForm.setItemCount(Integer.parseInt(String.valueOf(data.getData().getCount())));
			grid.setItems(listModel);
		}else {
			NotificationTemplate.warning("Không tìm thấy dữ liệu");
		}
	}
	
	private Component createGrid() {
		grid = new Grid<ApiLogIpsModel>(ApiLogIpsModel.class,false);
		
		grid.addColumn(model->{
			return LocalDateUtil.dfDateTime.format(model.getDate());
		}).setHeader("Ngày");
		
		grid.addColumn(model->{
			return model.getSrcIp().getIpAddress();
		}).setHeader("Địa chỉ nguồn");
		
		grid.addColumn(model->{
			return model.getDstIp().getIpAddress();
		}).setHeader("Địa chỉ đích");
		
		grid.addColumn(ApiLogIpsModel::getDstPort).setHeader("Cổng đích");
		grid.addColumn(ApiLogIpsModel::getProtocol).setHeader("Giao thức");
		grid.addColumn(ApiLogIpsModel::getSigName).setHeader("Tên mẫu nhận dạng");
		grid.addColumn(ApiLogIpsModel::getSourceIp).setHeader("Nguồn");
		
		return grid;
	}
	
	private ApiFilterLogIpsModel getSearch() {
		ApiFilterLogIpsModel apiFilterLogIpsModel = new ApiFilterLogIpsModel();
		
		apiFilterLogIpsModel.setSkip(paginationForm.getSkip());
		apiFilterLogIpsModel.setLimit(paginationForm.getLimit());
//		apiFilterLogIpsModel.setSourceIp(SessionUtil.getDeviceInfo().getIpAddress());
		apiFilterLogIpsModel.setSrcIp(apiLogIpsStaticModel.getSrcIp());
		apiFilterLogIpsModel.setDstIp(apiLogIpsStaticModel.getDstIp());
		apiFilterLogIpsModel.setDstPort(String.valueOf(apiLogIpsStaticModel.getDstPort()));
		apiFilterLogIpsModel.setProtocol(apiLogIpsStaticModel.getProtocol());
		apiFilterLogIpsModel.setSigName(apiLogIpsStaticModel.getSigName());
		apiFilterLogIpsModel.setFromDate(LocalDateUtil.localDateTimeToLong(fromDate.getValue()));
		apiFilterLogIpsModel.setToDate(LocalDateUtil.localDateTimeToLong(toDate.getValue()));
		
		System.out.println(apiFilterLogIpsModel);
		return apiFilterLogIpsModel;
	}
	

}
