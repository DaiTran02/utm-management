package com.ngn.utm.manager.views.realteach.log.config_change.form;

import com.ngn.utm.manager.api.real_tech.log.config_change.ApiLogConfigChangeModel;
import com.ngn.utm.manager.service.FormInterface;
import com.ngn.utm.manager.utils.LocalDateUtil;
import com.ngn.utm.manager.utils.commons.VerticalLayoutTemplate;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class DetailLogConfigChangeForm extends VerticalLayoutTemplate implements FormInterface{
	private static final long serialVersionUID = 1L;
	private ApiLogConfigChangeModel apiLogConfigChangeModel;
	
	private VerticalLayout vLayout = new VerticalLayout();
	
	public DetailLogConfigChangeForm(ApiLogConfigChangeModel apiLogConfigChangeModel) {
		this.apiLogConfigChangeModel = apiLogConfigChangeModel;
		buildLayout();
		configComponent();
		loadData();
	}
	
	@Override
	public void buildLayout() {
		this.setSizeFull();
		this.add(vLayout);
		vLayout.setSizeFull();
	}

	@Override
	public void configComponent() {
		
	}
	
	private void loadData() {
		vLayout.removeAll();
		String width = "200px";
		
		vLayout.add(createKeyValue("Ngày: ", LocalDateUtil.dfDateTime.format(apiLogConfigChangeModel.getDate()), width, true),
				createKeyValue("Reason: ", apiLogConfigChangeModel.getReason(), width, true),
				createKeyValue("Path: ", apiLogConfigChangeModel.getPath(), width, true),
				createKeyValue("Database: ", apiLogConfigChangeModel.getDatabase(), width, true),
				createKeyValue("Source Ip: ", apiLogConfigChangeModel.getSourceIp(), width, true),
				createKeyValue("Ip Address: ", apiLogConfigChangeModel.getIpAddress(), width, true),
				createKeyValue("Source Name: ", apiLogConfigChangeModel.getSourceName(), width, true),
				createKeyValue("User name: ", apiLogConfigChangeModel.getUserName(), width, true));
	}
	
	private Component createKeyValue(String name, String value, String width,boolean isBorder) {
		HorizontalLayout hLayoutKeyValue = new HorizontalLayout();

		H5 header = new H5(name);
		header.getStyle().setMargin("auto 0");
		header.setWidth(width);
		header.getStyle().setFlexShrink("0");

		Span spValue = new Span(value);

		hLayoutKeyValue.add(header,spValue);
		if(isBorder) {
			hLayoutKeyValue.getStyle().setBorderBottom("1px solid rgb(214 214 216)");
		}
		hLayoutKeyValue.setWidthFull();
		

		return hLayoutKeyValue;
	}

}
