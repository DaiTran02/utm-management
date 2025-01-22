package com.ngn.utm.manager.utils.commons;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.shared.Registration;

public class ConfirmDialogTemplate extends ConfirmDialog{
	private static final long serialVersionUID = 1L;
	ButtonTemplate btnCancel = new ButtonTemplate("Quay lại");
	ButtonTemplate btnConfirm = new ButtonTemplate("Xác nhận");

	public ConfirmDialogTemplate() {
		buildLayout();
		configComponent();
	}
	
	public ConfirmDialogTemplate(String text) {
		this.setHeader(text);
		buildLayout();
		configComponent();
	}
	
	public void buildLayout() {
		
		
		this.setCancelable(true);
		btnCancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
		this.setCancelButton(btnCancel);
		
		btnConfirm.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		this.setConfirmButton(btnConfirm);
	}

	public void configComponent() {
		
		btnCancel.addClickListener(e->{
			this.close();
		});
	}
	
	public void setDelete() {
		btnConfirm.addThemeVariants(ButtonVariant.LUMO_ERROR);
	}
	
	public ButtonTemplate getBtnConfirm() {
		return this.btnConfirm;
	}
	
	public Registration addChangeListener(ComponentEventListener<ClickEvent> listener) {
		return addListener(ClickEvent.class, listener);
	}

	public static class ClickEvent extends ComponentEvent<ConfirmDialogTemplate> {
		private static final long serialVersionUID = 1L;

		public ClickEvent(ConfirmDialogTemplate source, boolean fromClient) {
			super(source, fromClient);
		}
	}

}
