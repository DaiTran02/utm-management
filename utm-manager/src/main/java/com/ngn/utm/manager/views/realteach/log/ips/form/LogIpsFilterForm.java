package com.ngn.utm.manager.views.realteach.log.ips.form;

import java.time.LocalDateTime;

import org.vaadin.lineawesome.LineAwesomeIconUrl;

import com.ngn.utm.manager.api.real_tech.log.ips.ApiFilterLogIpsModel;
import com.ngn.utm.manager.service.FormInterface;
import com.ngn.utm.manager.utils.LocalDateUtil;
import com.ngn.utm.manager.utils.commons.ButtonTemplate;
import com.ngn.utm.manager.utils.commons.VerticalLayoutTemplate;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.datetimepicker.DateTimePickerVariant;
import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.dom.Style.AlignItems;
import com.vaadin.flow.dom.Style.FlexDirection;
import com.vaadin.flow.dom.Style.FlexWrap;

public class LogIpsFilterForm extends VerticalLayoutTemplate implements FormInterface{
	private static final long serialVersionUID = 1L;
	private DateTimePicker fromDate = new DateTimePicker("Từ ngày");
	private DateTimePicker toDate = new DateTimePicker("Đến ngày");
	
	private TextField txtIpAddress = new TextField("IpAddress");
	
	
	private Checkbox cbSrc = new Checkbox("Địa chỉ nguồn");
	private Checkbox cbDst = new Checkbox("Địa chỉ đích");
	
	private ButtonTemplate btnSeach = new ButtonTemplate("Tìm",new SvgIcon(LineAwesomeIconUrl.SEARCH_SOLID));
	private ButtonTemplate btnExport = new ButtonTemplate("Xuất CSV",new SvgIcon(LineAwesomeIconUrl.DOWNLOAD_SOLID));
	
	private FlexLayout flexLayout = new FlexLayout();
	

	public LogIpsFilterForm() {
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
		
		cbSrc.addClickListener(e->{
			if(cbSrc.getValue()) {
				cbDst.setValue(false);
			}
		});
		
		cbDst.addClickListener(e->{
			if(cbDst.getValue()) {
				cbSrc.setValue(false);
			}
		});
		
	}
	
	private void createLayout() {
		fromDate.addThemeVariants(DateTimePickerVariant.LUMO_SMALL);
		fromDate.setValue(LocalDateTime.now().minusMonths(1));
		fromDate.setLocale(LocalDateUtil.localeVietNam());
		
		toDate.addThemeVariants(DateTimePickerVariant.LUMO_SMALL);
		toDate.setValue(LocalDateTime.now());
		toDate.setLocale(LocalDateUtil.localeVietNam());
		
		txtIpAddress.addThemeVariants(TextFieldVariant.LUMO_SMALL);
		txtIpAddress.setPlaceholder("xxx.xxx.xxx.xxx");
		
		btnSeach.addThemeVariants(ButtonVariant.LUMO_SMALL);
		btnSeach.getStyle().setMarginTop("26px");
		
		cbSrc.getStyle().setMarginTop("25px");
		cbDst.getStyle().setMarginTop("25px");
		
		btnExport.addThemeVariants(ButtonVariant.LUMO_SMALL);
		btnExport.getStyle().setMarginTop("26px");
		btnExport.setDownload();
		
		flexLayout.add(fromDate,toDate,txtIpAddress,cbSrc,cbDst,btnSeach,btnExport,btnExport.getAnchor());
		flexLayout.getStyle().setFlexDirection(FlexDirection.ROW).set("gap", "10px").setFlexWrap(FlexWrap.WRAP).setAlignItems(AlignItems.CENTER);
	}
	
	public ApiFilterLogIpsModel getSearch() {
		ApiFilterLogIpsModel apiFilterLogIpsModel = new ApiFilterLogIpsModel();
		
		apiFilterLogIpsModel.setFromDate(LocalDateUtil.localDateTimeToLong(fromDate.getValue()));
		apiFilterLogIpsModel.setToDate(LocalDateUtil.localDateTimeToLong(toDate.getValue()));
		
		if(cbSrc.getValue()) {
			apiFilterLogIpsModel.setIpAddress(txtIpAddress.getValue());
			apiFilterLogIpsModel.setAddressType("src");
		}
		
		if(cbDst.getValue()) {
			apiFilterLogIpsModel.setIpAddress(txtIpAddress.getValue());
			apiFilterLogIpsModel.setAddressType("dst");
		}
		
		return apiFilterLogIpsModel;
	}
	


	public ButtonTemplate getBtnExport() {
		return btnExport;
	}

	public void setBtnExport(ButtonTemplate btnExport) {
		this.btnExport = btnExport;
	}
	
	

}
