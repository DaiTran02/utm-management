package com.ngn.utm.manager.views.realteach.log.connectivity.form;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import com.ngn.utm.manager.api.real_tech.log.connectivity.ApiFilterLogConnectivityModel;
import com.ngn.utm.manager.service.FormInterface;
import com.ngn.utm.manager.utils.LocalDateUtil;
import com.ngn.utm.manager.utils.commons.ButtonTemplate;
import com.ngn.utm.manager.utils.commons.VerticalLayoutTemplate;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.ComboBoxVariant;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.datetimepicker.DateTimePickerVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.dom.Style.AlignItems;
import com.vaadin.flow.dom.Style.FlexDirection;
import com.vaadin.flow.dom.Style.FlexWrap;

public class FilterLogConnectivityForm extends VerticalLayoutTemplate implements FormInterface{
	private static final long serialVersionUID = 1L;
	private DateTimePicker fromDate = new DateTimePicker("Từ ngày");
	private DateTimePicker toDate = new DateTimePicker("Đến ngày");
	
	private TextField txtSrcIp = new TextField("IP nguồn");
	private TextField txtSrcPort = new TextField("Port");
	
	private TextField txtDstIp = new TextField("IP Đích");
	private TextField txtDstPort = new TextField("Port");
	
	private ComboBox<Pair<String, String>> cmbAction = new ComboBox<Pair<String,String>>("Hành động");
	
	private ButtonTemplate btnSeach = new ButtonTemplate("Tìm",new SvgIcon(LineAwesomeIconUrl.SEARCH_SOLID));
	private ButtonTemplate btnExport = new ButtonTemplate("Xuất CSV",new SvgIcon(LineAwesomeIconUrl.DOWNLOAD_SOLID));
	
	private FlexLayout flexLayout = new FlexLayout();
	
	public FilterLogConnectivityForm() {
		buildLayout();
		configComponent();
		loadDataCmbAction();
	}

	@Override
	public void buildLayout() {
		this.setWidthFull();
		this.add(flexLayout);
		this.setPadding(false);
		createLayout();
	}

	@Override
	public void configComponent() {
		btnSeach.addClickListener(e->{
			fireEvent(new ClickEvent(this, false));
		});
		
	}
	
	private void createLayout() {
		fromDate.addThemeVariants(DateTimePickerVariant.LUMO_SMALL);
		fromDate.setValue(LocalDateTime.now().minusMonths(1));
		fromDate.setLocale(LocalDateUtil.localeVietNam());
		
		toDate.addThemeVariants(DateTimePickerVariant.LUMO_SMALL);
		toDate.setValue(LocalDateTime.now());
		toDate.setLocale(LocalDateUtil.localeVietNam());
		
		txtSrcIp.addThemeVariants(TextFieldVariant.LUMO_SMALL);
		txtSrcIp.setPlaceholder("xxx.xxx.xxx.xxx");
		txtSrcPort.addThemeVariants(TextFieldVariant.LUMO_SMALL);
		txtSrcPort.setPlaceholder("xxxx");
		
		txtDstIp.addThemeVariants(TextFieldVariant.LUMO_SMALL);
		txtDstIp.setPlaceholder("xxx.xxx.xxx.xxx");
		txtDstPort.addThemeVariants(TextFieldVariant.LUMO_SMALL);
		txtDstPort.setPlaceholder("xxxx");
		
		cmbAction.addThemeVariants(ComboBoxVariant.LUMO_SMALL);
		
		btnSeach.addThemeVariants(ButtonVariant.LUMO_SMALL);
		btnSeach.getStyle().setMarginTop("26px");
		
		btnExport.addThemeVariants(ButtonVariant.LUMO_SMALL);
		btnExport.getStyle().setMarginTop("26px");
		btnExport.setDownload();
		
		HorizontalLayout hLayoutSrc = new HorizontalLayout();
		Span spSrc = new Span("-");
		spSrc.getStyle().setMarginTop("20px");
		hLayoutSrc.add(txtSrcIp,spSrc,txtSrcPort);
		hLayoutSrc.setSpacing(false);
		hLayoutSrc.getStyle().setAlignItems(AlignItems.CENTER);
		
		HorizontalLayout hLayoutDst = new HorizontalLayout();
		Span spDst = new Span("-");
		spDst.getStyle().setMarginTop("20px");
		hLayoutDst.add(txtDstIp,spDst,txtDstPort);
		hLayoutDst.setSpacing(false);
		hLayoutDst.getStyle().setAlignItems(AlignItems.CENTER);
		
		
		flexLayout.add(fromDate,toDate,hLayoutSrc,hLayoutDst,cmbAction,btnSeach,btnExport,btnExport.getAnchor());
		flexLayout.getStyle().setFlexDirection(FlexDirection.ROW).set("gap", "10px").setFlexWrap(FlexWrap.WRAP).setAlignItems(AlignItems.CENTER);
	}
	
	private void loadDataCmbAction() {
		List<Pair<String, String>> results=new ArrayList<>();
		results.add(Pair.of(null,"Tất cả"));
		results.add(Pair.of("pass", "PASS"));
		results.add(Pair.of("block", "BLOCK"));
		results.add(Pair.of("allow", "ALLOW"));
		results.add(Pair.of("drop", "DROP"));
		
		cmbAction.setItems(results);
		cmbAction.setValue(results.get(0));
		cmbAction.setItemLabelGenerator(Pair::getValue);
	}
	
	public ApiFilterLogConnectivityModel getSearch() {
		ApiFilterLogConnectivityModel apiFilterLogConnectivityModel = new ApiFilterLogConnectivityModel();
		
		apiFilterLogConnectivityModel.setFromDate(LocalDateUtil.localDateTimeToLong(fromDate.getValue()));
		apiFilterLogConnectivityModel.setToDate(LocalDateUtil.localDateTimeToLong(toDate.getValue()));
		
		apiFilterLogConnectivityModel.setSrcIp(txtSrcIp.getValue());
		apiFilterLogConnectivityModel.setSrcPort(txtSrcPort.getValue());
		
		apiFilterLogConnectivityModel.setDstIp(txtDstIp.getValue());
		apiFilterLogConnectivityModel.setDstPort(txtDstPort.getValue());
		
		apiFilterLogConnectivityModel.setAction(cmbAction.getValue().getKey());
		
		return apiFilterLogConnectivityModel;
	}

	public ButtonTemplate getBtnExport() {
		return btnExport;
	}

	public void setBtnExport(ButtonTemplate btnExport) {
		this.btnExport = btnExport;
	}
}
