package com.ngn.utm.manager.views.realteach.log.login_ssh.form;

import com.ngn.utm.manager.api.real_tech.log.login_ssh.ApiLoginSSHModel;
import com.ngn.utm.manager.service.FormInterface;
import com.ngn.utm.manager.utils.LocalDateUtil;
import com.ngn.utm.manager.utils.commons.VerticalLayoutTemplate;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class LoginSSHDetailForm extends VerticalLayoutTemplate implements FormInterface{
	private static final long serialVersionUID = 1L;
	
	private ApiLoginSSHModel apiLoginSSHModel;
	
	private VerticalLayout vLayout = new VerticalLayout();
	
	public LoginSSHDetailForm(ApiLoginSSHModel apiLoginSSHModel) {
		this.apiLoginSSHModel = apiLoginSSHModel;
		buildLayout();
		configComponent();
		loadData();
	}
	
	@Override
	public void buildLayout() {
		this.setSizeFull();
		this.add(vLayout);
		vLayout.setSizeFull();
		createLayout();
	}

	@Override
	public void configComponent() {
		
	}
	
	private void loadData() {
	}
	
	private void createLayout() {
		vLayout.removeAll();
		
		String width = "150px";
		
		vLayout.add(createKeyValue("Ngày: ", LocalDateUtil.dfDateTime.format(apiLoginSSHModel.getDate()), width, true),
				createKeyValue("Kết quả: ", apiLoginSSHModel.getResult(), width, true),
				createKeyValue("Giao thức: ", apiLoginSSHModel.getProtocol(), width, true),
				createKeyValue("SourceIp: ", apiLoginSSHModel.getSourceIp(), width, true),
				createKeyValue("Method: ", apiLoginSSHModel.getMethod(), width, true),
				createKeyValue("Từ IP: ", apiLoginSSHModel.getFromIp(), width, true),
				createKeyValue("Source Name", apiLoginSSHModel.getSourceName(), width, true),
				createKeyValue("Port", apiLoginSSHModel.getPortNumber(), width, true));
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
