package com.ngn.utm.manager.views.pfsenses.alias.form.type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.vaadin.lineawesome.LineAwesomeIconUrl;

import com.ngn.utm.manager.service.FormInterface;
import com.ngn.utm.manager.utils.commons.ButtonTemplate;
import com.ngn.utm.manager.utils.commons.HeaderComponent;
import com.ngn.utm.manager.views.pfsenses.alias.form.type.model.TypeGeneralModel;
import com.ngn.utm.manager.views.pfsenses.alias.form.type.model.TypeNetworkModel;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class TypeUrlTableIpsForm extends TypeBaseForm implements FormInterface{
	private static final long serialVersionUID = 1L;
	private HeaderComponent headerHost = new HeaderComponent("URL Table(Ips)");
	private ButtonTemplate btnAddNewHost = new ButtonTemplate("Add", new SvgIcon(LineAwesomeIconUrl.PLUS_SOLID));
	private VerticalLayout vLayoutHost = new VerticalLayout();
	private Map<String, Component> mapItems = new HashMap<String, Component>();
	private Map<String, TypeNetworkModel> mapValueOfItem = new HashMap<String, TypeNetworkModel>();
	private int idItems = 0;
	
	public TypeUrlTableIpsForm() {
		buildLayout();
		configComponent();
	}

	@Override
	public void buildLayout() {
		this.setWidthFull();
		this.add(headerHost);
		this.setPadding(false);
		createLayout();
	}

	@Override
	public void configComponent() {
		btnAddNewHost.addClickListener(e->{
			addNewHost(null,-1,null);
		});
	}
	
	@Override
	public void loadData(List<TypeGeneralModel> list) {
		list.forEach(model->{
			String[] address = model.getIp().split("/");
			addNewHost(address[0], Integer.valueOf(address[1]), model.getDesr());
		});
		
	}
	
	private void createLayout() {
		VerticalLayout vLayout = new VerticalLayout();
		vLayout.setWidthFull();
		String width = "100px";
		vLayout.add(createKeyValue("Hint", "Enter a single URL containing a large number of IPs and/or Subnets. After saving, the URLs will be downloaded and a table file containing these addresses will be created. This will work with large numbers of addresses (30,000+) or small numbers.\r\n"
				+ "The value after the \"/\" is the update frequency in days.", width, true));
		vLayout.add(btnAddNewHost);
		btnAddNewHost.getStyle().setMarginLeft("auto");
		
		vLayout.add(createKeyComponent("IP or FQDN", vLayoutHost, width, false));
		
		headerHost.addLayout(vLayout);
	}
	
	
	private void addNewHost(String address,int port,String desr) {
		Component component = createField(address, port, desr);
		vLayoutHost.add(component);
	}
	
	private void refreshAllItems() {
		vLayoutHost.removeAll();
		mapItems.forEach((k,v)->{
			vLayoutHost.add(v);
		});
	}
	
	private void removeItem(String id) {
		mapItems.remove(id);
		mapValueOfItem.remove(id);
		refreshAllItems();
	}
	
	private Component createField(String address,int port,String desr) {
		TextField txtAddress = new TextField();
		txtAddress.setPlaceholder("Address");
		txtAddress.setWidth("48%");
		if(address != null) {
			txtAddress.setValue(address);
		}
		
		ComboBox<Integer> cmbPort = new ComboBox<Integer>();
		List<Integer> listPort = new ArrayList<Integer>();
		for(int i = 0; i <= 128;i++) {
			listPort.add(i);
		}
		
		cmbPort.setWidth("100px");
		cmbPort.setItems(listPort);
		cmbPort.setValue(listPort.get(listPort.size()-1));
		if(port != -1) {
			cmbPort.setValue(port);
		}
		
		TextField txtDesr = new TextField();
		txtDesr.setPlaceholder("Description");
		txtDesr.setWidth("48%");
		if(desr != null) {
			txtDesr.setValue(desr);
		}
		
		ButtonTemplate btnRemove = new ButtonTemplate(new SvgIcon(LineAwesomeIconUrl.WINDOW_CLOSE_SOLID));
		btnRemove.addThemeVariants(ButtonVariant.LUMO_ERROR);
		btnRemove.setId(String.valueOf(idItems));
		btnRemove.addClickListener(e->{
			btnRemove.getId().ifPresent(i->{
				removeItem(i);
			});
		});
		
		HorizontalLayout hLayoutItem = new HorizontalLayout();
		hLayoutItem.setWidthFull();
		hLayoutItem.add(txtAddress,cmbPort,txtDesr,btnRemove);
		
		TypeNetworkModel typeNetworkModel = new TypeNetworkModel(txtAddress,cmbPort,txtDesr);
		
		
		Component component = hLayoutItem;
		mapItems.put(String.valueOf(idItems), component);
		mapValueOfItem.put(String.valueOf(idItems), typeNetworkModel);
		
		idItems++;
		
		return hLayoutItem;
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
	

	private Component createKeyComponent(String name, Component value, String width,boolean isBorder) {
		HorizontalLayout hLayoutKeyValue = new HorizontalLayout();

		H5 header = new H5(name);
		header.getStyle().setMargin("auto 0");
		header.setWidth(width);
		header.getStyle().setFlexShrink("0");


		hLayoutKeyValue.add(header,value);
		if(isBorder) {
			hLayoutKeyValue.getStyle().setBorderBottom("1px solid rgb(214 214 216)");
		}
		hLayoutKeyValue.setWidthFull();
		

		return hLayoutKeyValue;
	}
	
	public List<TypeGeneralModel> getListValues(){
		List<TypeGeneralModel> listData = new ArrayList<TypeGeneralModel>();
		mapValueOfItem.forEach((k,v)->{
			listData.add(new TypeGeneralModel(v.getTxtip().getValue(), v.getCmbPort().getValue(),v.getTxtDesr().getValue()));
		});
		
		return listData;
	}

}
