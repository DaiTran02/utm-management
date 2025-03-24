package com.ngn.utm.manager.views.pfsenses.alias.form.type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import com.ngn.utm.manager.service.FormInterface;
import com.ngn.utm.manager.utils.commons.ButtonTemplate;
import com.ngn.utm.manager.utils.commons.HeaderComponent;
import com.ngn.utm.manager.views.pfsenses.alias.form.type.model.TypeGeneralModel;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class TypeUrlPortsForm extends TypeBaseForm implements FormInterface{
	private static final long serialVersionUID = 1L;
	private HeaderComponent headerHost = new HeaderComponent("URL(Ports)");
	private ButtonTemplate btnAddNewHost = new ButtonTemplate("Add", new SvgIcon(LineAwesomeIconUrl.PLUS_SOLID));
	private VerticalLayout vLayoutHost = new VerticalLayout();
	private Map<String, Component> mapItems = new HashMap<String, Component>();
	private Map<String, Pair<TextField, TextField>> mapValueOfItem = new HashMap<String, Pair<TextField,TextField>>();
	private int idItems = 0;
	
	public TypeUrlPortsForm() {
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
			addNewHost(null,null);
		});
	}
	
	@Override
	public void loadData(List<TypeGeneralModel> list) {
		list.forEach(model->{
			addNewHost(model.getIp(), model.getDesr());
		});
	}
	
	private void createLayout() {
		VerticalLayout vLayout = new VerticalLayout();
		vLayout.setWidthFull();
		String width = "100px";
		vLayout.add(createKeyValue("Hint", "Enter as many URLs as desired. "
				+ "After saving, the URLs will be downloaded and the items imported into the alias. "
				+ "Use only with small sets of Ports (less than 3000).", width, true));
		vLayout.add(btnAddNewHost);
		btnAddNewHost.getStyle().setMarginLeft("auto");
		
		vLayout.add(createKeyComponent("IP or FQDN", vLayoutHost, width, false));
		
		headerHost.addLayout(vLayout);
	}
	
	
	private void addNewHost(String address,String desr) {
		Component component = createField(address, desr);
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
	
	private Component createField(String address,String desr) {
		TextField txtAddress = new TextField();
		txtAddress.setPlaceholder("Address");
		txtAddress.setWidth("48%");
		if(address != null) {
			txtAddress.setValue(address);
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
		hLayoutItem.add(txtAddress,txtDesr,btnRemove);
		
		Component component = hLayoutItem;
		mapItems.put(String.valueOf(idItems), component);
		mapValueOfItem.put(String.valueOf(idItems), Pair.of(txtAddress,txtDesr));
		
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
			listData.add(new TypeGeneralModel(v.getKey().getValue(), -1, v.getValue().getValue()));
		});
		
		return listData;
	}

}
