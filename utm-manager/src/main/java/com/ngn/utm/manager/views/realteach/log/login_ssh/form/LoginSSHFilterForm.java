package com.ngn.utm.manager.views.realteach.log.login_ssh.form;

import java.time.LocalDateTime;

import org.vaadin.lineawesome.LineAwesomeIconUrl;

import com.ngn.utm.manager.api.real_tech.log.login_ssh.ApiFilterLoginSSHModel;
import com.ngn.utm.manager.service.FormInterface;
import com.ngn.utm.manager.utils.LocalDateUtil;
import com.ngn.utm.manager.utils.commons.ButtonTemplate;
import com.ngn.utm.manager.utils.commons.VerticalLayoutTemplate;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.datetimepicker.DateTimePickerVariant;
import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;

public class LoginSSHFilterForm extends VerticalLayoutTemplate implements FormInterface{
	private static final long serialVersionUID = 1L;
	private DateTimePicker fromDate = new DateTimePicker("Từ ngày");
	private DateTimePicker toDate = new DateTimePicker("Đến ngày");
	private TextField txtFromIp = new TextField("Từ địa chỉ");
	private ButtonTemplate btnSeach = new ButtonTemplate("Tìm",new SvgIcon(LineAwesomeIconUrl.SEARCH_SOLID));
	private ButtonTemplate btnExport = new ButtonTemplate("Xuất CSV",new SvgIcon(LineAwesomeIconUrl.DOWNLOAD_SOLID));
	private HorizontalLayout hLayoutFilter = new HorizontalLayout();
	public LoginSSHFilterForm() {
		buildLayout();
		configComponent();
	}

	@Override
	public void buildLayout() {
		this.setWidthFull();
		this.add(hLayoutFilter);
		createLayoutFilter();
		
	}

	@Override
	public void configComponent() {
		btnSeach.addClickListener(e->{
			fireEvent(new ClickEvent(this, false));
		});
		
	}
	
	private void createLayoutFilter() {
		fromDate.addThemeVariants(DateTimePickerVariant.LUMO_SMALL);
		fromDate.setValue(LocalDateTime.now().minusMonths(1));
		fromDate.setLocale(LocalDateUtil.localeVietNam());
		
		toDate.addThemeVariants(DateTimePickerVariant.LUMO_SMALL);
		toDate.setValue(LocalDateTime.now());
		toDate.setLocale(LocalDateUtil.localeVietNam());
		
		txtFromIp.addThemeVariants(TextFieldVariant.LUMO_SMALL);
		txtFromIp.setPlaceholder("xxx.xxx.xxx.xxx");
		
		btnSeach.addThemeVariants(ButtonVariant.LUMO_SMALL);
		btnSeach.getStyle().setMarginTop("26px");
		
		btnExport.addThemeVariants(ButtonVariant.LUMO_SMALL);
		btnExport.getStyle().setMarginTop("26px");
		btnExport.setDownload();
		
		hLayoutFilter.removeAll();
		hLayoutFilter.add(fromDate,toDate,txtFromIp,btnSeach,btnExport,btnExport.getAnchor());
	}
	
	public ApiFilterLoginSSHModel getSearch() {
		ApiFilterLoginSSHModel apiFilterLoginSSHModel = new ApiFilterLoginSSHModel();
		
		apiFilterLoginSSHModel.setFromDate(LocalDateUtil.localDateTimeToLong(fromDate.getValue()));
		apiFilterLoginSSHModel.setToDate(LocalDateUtil.localDateTimeToLong(toDate.getValue()));
		
		apiFilterLoginSSHModel.setFromIp(txtFromIp.getValue().isEmpty() ? null : txtFromIp.getValue());
		
		
		return apiFilterLoginSSHModel;
	}

	public ButtonTemplate getBtnExport() {
		return btnExport;
	}

	public void setBtnExport(ButtonTemplate btnExport) {
		this.btnExport = btnExport;
	}
	
	

}
