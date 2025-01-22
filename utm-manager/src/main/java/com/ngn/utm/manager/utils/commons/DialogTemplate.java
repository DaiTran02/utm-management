package com.ngn.utm.manager.utils.commons;

import org.vaadin.lineawesome.LineAwesomeIconUrl;

import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.SvgIcon;

public class DialogTemplate extends Dialog{
	private static final long serialVersionUID = 1L;
	
	private ButtonTemplate btnClose = new ButtonTemplate(new SvgIcon(LineAwesomeIconUrl.WINDOW_CLOSE_SOLID));
	private ButtonTemplate btnSave = new ButtonTemplate("Lưu", new SvgIcon(LineAwesomeIconUrl.SAVE));
	
	public DialogTemplate() {
		super();
		buildLayout();
	}
	
	public DialogTemplate(String nameDialog) {
		super();
		this.setHeaderTitle(nameDialog);
		buildLayout();
	}
	
	private void buildLayout() {
		btnClose.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
		btnClose.addThemeVariants(ButtonVariant.LUMO_ERROR);
		btnClose.addClickListener(e->this.close());
		
		this.getHeader().add(btnClose);
		
		btnSave.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
		btnSave.getStyle().setMarginLeft("auto");
		this.getFooter().add(btnSave);
	}

	public ButtonTemplate getBtnClose() {
		return btnClose;
	}

	public void setBtnClose(ButtonTemplate btnClose) {
		this.btnClose = btnClose;
	}

	public ButtonTemplate getBtnSave() {
		return btnSave;
	}

	public void setBtnSave(ButtonTemplate btnSave) {
		this.btnSave = btnSave;
	}
}
