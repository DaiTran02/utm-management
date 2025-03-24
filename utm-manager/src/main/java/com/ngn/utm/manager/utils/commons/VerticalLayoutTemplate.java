package com.ngn.utm.manager.utils.commons;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.ngn.utm.manager.api.real_tech.host.ApiHostModel;
import com.ngn.utm.manager.utils.MainLayoutUtil;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;

public class VerticalLayoutTemplate extends VerticalLayout{
	private static final long serialVersionUID = 1L;
	
	public VerticalLayoutTemplate() {
		
	}
	
	public void refreshMainLayout() {
	}
	
	public void refreshListDevice(List<Pair<ApiHostModel, String>> listDevice,ApiHostModel apiHostModel) {
		getUI().ifPresent(ui -> ui.access(()->{
			MainLayoutUtil.getMainLayout().loadNewDevices(listDevice,apiHostModel);
			System.out.println("Alo 2");
		}));
	}
	
	public Registration addChangeListener(ComponentEventListener<ClickEvent> listener) {
		return addListener(ClickEvent.class, listener);
	}

	public static class ClickEvent extends ComponentEvent<VerticalLayoutTemplate> {
		private static final long serialVersionUID = 1L;

		public ClickEvent(VerticalLayoutTemplate source, boolean fromClient) {
			super(source, fromClient);
		}
	}
}
