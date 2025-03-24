package com.ngn.utm.manager.views.pfsenses.config.form;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.lineawesome.LineAwesomeIconUrl;

import com.ngn.utm.manager.api.ApiResultResponse;
import com.ngn.utm.manager.api.pfsenses.config.ApiConfigModel;
import com.ngn.utm.manager.api.pfsenses.config.ApiConfigService;
import com.ngn.utm.manager.service.FormInterface;
import com.ngn.utm.manager.utils.LocalDateUtil;
import com.ngn.utm.manager.utils.SessionUtil;
import com.ngn.utm.manager.utils.commons.ButtonTemplate;
import com.ngn.utm.manager.utils.commons.CantConnectToPfsenseForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class ConfigForm extends VerticalLayout implements FormInterface{
	private static final long serialVersionUID = 1L;
	private ApiConfigService apiConfigService;
	private Grid<ApiConfigModel> grid = new Grid<ApiConfigModel>(ApiConfigModel.class,false);
	private List<ApiConfigModel> listModel = new ArrayList<ApiConfigModel>();
	
	public ConfigForm(ApiConfigService apiConfigService) {
		this.apiConfigService = apiConfigService;
		buildLayout();
		configComponent();
		loadData();
	}

	@Override
	public void buildLayout() {
		this.setSizeFull();
		this.add(createGrid());
		
	}

	@Override
	public void configComponent() {
		
	}
	
	private void loadData() {
		listModel = new ArrayList<ApiConfigModel>();
			try {
				ApiResultResponse<List<ApiConfigModel>> data = apiConfigService.getConfig(SessionUtil.getDeviceInfo().getId());
				if(data.isSuccess()) {
					listModel.addAll(data.getData());
				}
			} catch (Exception e) {
				this.removeAll();
				CantConnectToPfsenseForm cantConnectToPfsenseForm = new CantConnectToPfsenseForm();
				this.add(cantConnectToPfsenseForm);
			}

		grid.setItems(listModel);
	}
	
	private Component createGrid() {
		grid = new Grid<ApiConfigModel>(ApiConfigModel.class,false);
		
		
		grid.addColumn(ApiConfigModel::getFileName).setHeader("Tên");
		grid.addComponentColumn(model->{
			return new Span(LocalDateUtil.dfDateTime.format(model.getCreatedDate()));
		}).setHeader("Ngày tạo");
		
		grid.addComponentColumn(e->{
			HorizontalLayout hLayout = new HorizontalLayout();
			ButtonTemplate btnDownload = new ButtonTemplate("Tải xuống",new SvgIcon(LineAwesomeIconUrl.DOWNLOAD_SOLID));
			
			hLayout.add(btnDownload,btnDownload.getAnchor());
			
			return hLayout;
		}).setWidth("150px").setFlexGrow(0);
		
		grid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
		return grid;
	}
	

}
