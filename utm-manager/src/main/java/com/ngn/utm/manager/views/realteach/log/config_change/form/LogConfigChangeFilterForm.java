package com.ngn.utm.manager.views.realteach.log.config_change.form;

import java.time.LocalDateTime;

import org.vaadin.lineawesome.LineAwesomeIconUrl;

import com.ngn.utm.manager.api.real_tech.log.config_change.ApiFilterLogConfigChangeModel;
import com.ngn.utm.manager.service.FormInterface;
import com.ngn.utm.manager.utils.LocalDateUtil;
import com.ngn.utm.manager.utils.commons.ButtonTemplate;
import com.ngn.utm.manager.utils.commons.VerticalLayoutTemplate;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.datetimepicker.DateTimePickerVariant;
import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.dom.Style.AlignItems;
import com.vaadin.flow.dom.Style.FlexDirection;
import com.vaadin.flow.dom.Style.FlexWrap;

public class LogConfigChangeFilterForm extends VerticalLayoutTemplate implements FormInterface{
	private static final long serialVersionUID = 1L;
	private DateTimePicker fromDate = new DateTimePicker("Từ ngày");
	private DateTimePicker toDate = new DateTimePicker("Đến ngày");
	private TextField txtUserName = new TextField("User name");
	private TextField txtIpAddress = new TextField("Địa chỉ IP");
	private TextField txtReason = new TextField("Reason");
	
	private ButtonTemplate btnSeach = new ButtonTemplate("Tìm",new SvgIcon(LineAwesomeIconUrl.SEARCH_SOLID));
	private ButtonTemplate btnExport = new ButtonTemplate("Xuất CSV",new SvgIcon(LineAwesomeIconUrl.DOWNLOAD_SOLID));
	
	private FlexLayout flexLayout = new FlexLayout();
	
	public LogConfigChangeFilterForm() {
		buildLayout();
		configComponent();
	}

	@Override
	public void buildLayout() {
		this.setWidthFull();
		this.add(flexLayout);
		createLayout();
	}

	@Override
	public void configComponent() {
		btnSeach.addClickListener(e->{
			fireEvent(new ClickEvent(this, false));
		});
	}
	
	private void createLayout() {
		flexLayout.removeAll();
		
		fromDate.addThemeVariants(DateTimePickerVariant.LUMO_SMALL);
		fromDate.setValue(LocalDateTime.now().minusMonths(1));
		fromDate.setLocale(LocalDateUtil.localeVietNam());
		
		toDate.addThemeVariants(DateTimePickerVariant.LUMO_SMALL);
		toDate.setValue(LocalDateTime.now());
		toDate.setLocale(LocalDateUtil.localeVietNam());
		
		txtUserName.addThemeVariants(TextFieldVariant.LUMO_SMALL);
		txtUserName.setPlaceholder("ex: aquafino");
		
		txtIpAddress.addThemeVariants(TextFieldVariant.LUMO_SMALL);
		txtIpAddress.setPlaceholder("xxx.xxx.xxx.xxx");
		
		txtReason.addThemeVariants(TextFieldVariant.LUMO_SMALL);
		txtReason.setPlaceholder("ex: Config has been ...");
		
		btnSeach.addThemeVariants(ButtonVariant.LUMO_SMALL);
		btnSeach.getStyle().setMarginTop("26px");
		
		btnExport.addThemeVariants(ButtonVariant.LUMO_SMALL);
		btnExport.getStyle().setMarginTop("26px");
		btnExport.setDownload();
		
		flexLayout.add(fromDate,toDate,txtUserName,txtIpAddress,txtReason,btnSeach,btnExport,btnExport.getAnchor());
		flexLayout.getStyle().setFlexDirection(FlexDirection.ROW).set("gap", "10px").setFlexWrap(FlexWrap.WRAP).setAlignItems(AlignItems.CENTER);
	}
	
	public ApiFilterLogConfigChangeModel getSearch() {
		ApiFilterLogConfigChangeModel apiFilterLogConfigChangeModel = new ApiFilterLogConfigChangeModel();
		
		apiFilterLogConfigChangeModel.setFromDate(LocalDateUtil.localDateTimeToLong(fromDate.getValue()));
		apiFilterLogConfigChangeModel.setToDate(LocalDateUtil.localDateTimeToLong(toDate.getValue()));
		apiFilterLogConfigChangeModel.setUsername(txtUserName.getValue().isEmpty() ? null : txtUserName.getValue());
		apiFilterLogConfigChangeModel.setIpAddress(txtIpAddress.getValue().isEmpty() ? null : txtIpAddress.getValue());
		apiFilterLogConfigChangeModel.setReason(txtReason.getValue().isBlank() ? null : txtReason.getValue());
		
		return apiFilterLogConfigChangeModel;
	}

	public ButtonTemplate getBtnExport() {
		return btnExport;
	}

	public void setBtnExport(ButtonTemplate btnExport) {
		this.btnExport = btnExport;
	}
}
