package com.ngn.utm.manager.utils.commons;

import com.ngn.utm.manager.service.FormInterface;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.Style.AlignItems;
import com.vaadin.flow.dom.Style.JustifyContent;

public class CantConnectToPfsenseForm extends VerticalLayoutTemplate implements FormInterface{
	private static final long serialVersionUID = 1L;
	
	private VerticalLayout vLayout = new VerticalLayout();
	
	public CantConnectToPfsenseForm() {
		buildLayout();
		configComponent();
	}

	@Override
	public void buildLayout() {
		this.setSizeFull();
		this.add(vLayout);
		
		vLayout.getStyle().setJustifyContent(JustifyContent.CENTER).setAlignItems(AlignItems.CENTER);
		
		createLayout();
	}

	@Override
	public void configComponent() {
		
	}
	
	private void createLayout() {
		vLayout.removeAll();
		
		H2 header = new H2("Không thể kết nối tới thiết bị");
		Span spTitle = new Span("UTM Manager không thể kết nối tới thiết bị bạn đã thêm vào vui lòng kiểm tra lại ClientId và ClientToken"
				+ " hoặc và chắc chắn rằng thiết bị được cấu hình là https khi kết nối tới UTM Manager");
		
		Image image = new Image("./icons/connect.png", "image");
		image.setWidth("200px");
		
		
		vLayout.add(header,spTitle,image);
		
		
	}

}
