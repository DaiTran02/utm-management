package com.ngn.utm.manager.views.pfsenses.alias.form;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.ngn.utm.manager.api.pfsenses.ApiResultPfsenseResponse;
import com.ngn.utm.manager.api.pfsenses.alias.ApiAliasService;
import com.ngn.utm.manager.api.pfsenses.alias.ApiCreateAndUpdateAliasModel;
import com.ngn.utm.manager.api.pfsenses.alias.ApiReadFirewallAliasModel;
import com.ngn.utm.manager.service.FormInterface;
import com.ngn.utm.manager.utils.commons.HeaderComponent;
import com.ngn.utm.manager.utils.commons.VerticalLayoutTemplate;
import com.ngn.utm.manager.views.pfsenses.alias.enums.AliasEnum;
import com.ngn.utm.manager.views.pfsenses.alias.form.type.TypeBaseForm;
import com.ngn.utm.manager.views.pfsenses.alias.form.type.TypeHostForm;
import com.ngn.utm.manager.views.pfsenses.alias.form.type.TypeNetworkForm;
import com.ngn.utm.manager.views.pfsenses.alias.form.type.TypePortForm;
import com.ngn.utm.manager.views.pfsenses.alias.form.type.TypeUrlPortsForm;
import com.ngn.utm.manager.views.pfsenses.alias.form.type.TypeUrlTableIpsForm;
import com.ngn.utm.manager.views.pfsenses.alias.form.type.TypeUrlTablePortsForm;
import com.ngn.utm.manager.views.pfsenses.alias.form.type.TypeUrlipsForm;
import com.ngn.utm.manager.views.pfsenses.alias.form.type.model.TypeGeneralModel;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class FirewallAliasIpEditForm extends VerticalLayoutTemplate implements FormInterface{
	private static final long serialVersionUID = 1L;
	
	private final ApiAliasService apiAliasService;
	
	private HeaderComponent headerProperties = new HeaderComponent("Properties");
	private TextField txtName = new TextField("Name");
	private TextField txtDesr = new TextField("Description");
	private ComboBox<AliasEnum> cmbType = new ComboBox<AliasEnum>("Type");
	private TypeHostForm typeHostForm = new TypeHostForm();
	private TypeNetworkForm typeNetworkForm = new TypeNetworkForm();
	private TypePortForm typePortForm = new TypePortForm();
	private TypeUrlipsForm typeUrlipsForm = new TypeUrlipsForm();
	private TypeUrlPortsForm typeUrlPortsForm = new TypeUrlPortsForm();
	private TypeUrlTableIpsForm typeUrlTableIpsForm = new TypeUrlTableIpsForm();
	private TypeUrlTablePortsForm typeUrlTablePortsForm = new TypeUrlTablePortsForm();
	
	private VerticalLayout vLayoutType = new VerticalLayout();
	
	private ApiReadFirewallAliasModel apiReadFirewallAliasModel;
	public FirewallAliasIpEditForm(ApiAliasService apiAliasService,ApiReadFirewallAliasModel apiReadFirewallAliasModel) {
		this.apiAliasService = apiAliasService;
		this.apiReadFirewallAliasModel = apiReadFirewallAliasModel;
		buildLayout();
		configComponent();
		loadType();
		if(apiReadFirewallAliasModel != null) {
			loadData();
		}
	}

	@Override
	public void buildLayout() {
		this.setSizeFull();
		this.add(headerProperties);
		createLayoutProperties();
		this.add(vLayoutType);
		vLayoutType.setWidthFull();
		vLayoutType.setPadding(false);
		
	}

	@Override
	public void configComponent() {
		cmbType.addValueChangeListener(e->{
			changeType();
		});
	}
	
	
	private void createLayoutProperties() {
		VerticalLayout vLayoutProperties = new VerticalLayout();
		vLayoutProperties.setWidthFull();
		
		txtName.setWidthFull();
		txtName.setHelperText("The name of the alias may only consist of the characters \"a-z, A-Z, 0-9 and _\".");
		
		txtDesr.setWidthFull();
		txtDesr.setHelperText("A description may be entered here for administrative reference (not parsed).");
		
		cmbType.setWidthFull();
		
		vLayoutProperties.add(txtName,txtDesr,cmbType);
		headerProperties.addLayout(vLayoutProperties);
		
	}
	
	private void loadData() {
		txtName.setValue(apiReadFirewallAliasModel.getName());
		txtDesr.setValue(apiReadFirewallAliasModel.getDescr() == null ? "" : apiReadFirewallAliasModel.getDescr());
		cmbType.setValue(AliasEnum.findByKey(apiReadFirewallAliasModel.getType()));
		List<TypeGeneralModel> listData = new ArrayList<TypeGeneralModel>();
		List<String> listAddress = new ArrayList<String>(Arrays.asList(apiReadFirewallAliasModel.getAddress().split(" ")));
		
		String stringDetails = apiReadFirewallAliasModel.getDetail();
		stringDetails = stringDetails.replace("||", "-");
		List<String> listDetails = new ArrayList<String>(Arrays.asList(stringDetails.split("-")));
		
		for(int i = 0 ; i <= listAddress.size()-1;i++) {
			if(apiReadFirewallAliasModel.getDescr() != null) {
				listData.add(new TypeGeneralModel(listAddress.get(i), -1,listDetails.get(i)));
			}else {
				listData.add(new TypeGeneralModel(listAddress.get(i), -1,""));
			}
		}
		
		loadDataForType(listData);
	}
	
	private void loadDataForType(List<TypeGeneralModel> listData) {
		Map<String, TypeBaseForm> mapType = Map.of(
				AliasEnum.HOST.getKey(),typeHostForm,
				AliasEnum.NETWORK.getKey(),typeNetworkForm,
				AliasEnum.PORT.getKey(),typePortForm,
				AliasEnum.URL.getKey(),typeUrlipsForm,
				AliasEnum.URL_PORTS.getKey(),typeUrlPortsForm,
				AliasEnum.URLTABLE.getKey(),typeUrlTableIpsForm,
				AliasEnum.URLTABLE_PORTS.getKey(),typeUrlTablePortsForm);
		
		mapType.getOrDefault(cmbType.getValue().getKey(), typeHostForm).loadData(listData);
	}
	
	
	private void loadType() {
		List<AliasEnum> listType = new ArrayList<AliasEnum>();
		for(AliasEnum aliasEnum : AliasEnum.values()) {
			listType.add(aliasEnum);
		}
		
		cmbType.setItems(listType);
		cmbType.setItemLabelGenerator(AliasEnum::getTitle);
		cmbType.setValue(listType.get(0));
	}
	
	private void changeType() {
		vLayoutType.removeAll();
		Map<String, Component> mapType = Map.of(
				AliasEnum.HOST.getKey(),typeHostForm,
				AliasEnum.NETWORK.getKey(),typeNetworkForm,
				AliasEnum.PORT.getKey(),typePortForm,
				AliasEnum.URL.getKey(),typeUrlipsForm,
				AliasEnum.URL_PORTS.getKey(),typeUrlPortsForm,
				AliasEnum.URLTABLE.getKey(),typeUrlTableIpsForm,
				AliasEnum.URLTABLE_PORTS.getKey(),typeUrlTablePortsForm);
		
		Component form = mapType.getOrDefault(cmbType.getValue().getKey(), typeUrlTablePortsForm);
		vLayoutType.add(form);
	}
	
	public void save() {
		
		Map<String, List<TypeGeneralModel>> mapValues = Map.of(
				AliasEnum.HOST.getKey(),typeHostForm.getListValues(),
				AliasEnum.NETWORK.getKey(),typeNetworkForm.getListValues(),
				AliasEnum.PORT.getKey(),typePortForm.getListValues(),
				AliasEnum.URL.getKey(),typeUrlipsForm.getListValues(),
				AliasEnum.URL_PORTS.getKey(),typeUrlPortsForm.getListValues(),
				AliasEnum.URLTABLE.getKey(),typeUrlTableIpsForm.getListValues(),
				AliasEnum.URLTABLE_PORTS.getKey(),typeUrlTablePortsForm.getListValues()
				);
		List<String> listAddress = new ArrayList<String>();
		List<String> listDetails = new ArrayList<String>();
		mapValues.get(cmbType.getValue().getKey()).forEach(model->{
			
			listDetails.add(model.getDesr());
			if(model.getPort() != -1) {
				listAddress.add(model.getIp()+"/"+model.getPort());
			}else {
				listAddress.add(model.getIp());
			}
		});
		
		ApiCreateAndUpdateAliasModel apiCreateAndUpdateAliasModel = new ApiCreateAndUpdateAliasModel();
		apiCreateAndUpdateAliasModel.setName(txtName.getValue());
		apiCreateAndUpdateAliasModel.setApply(true);
		apiCreateAndUpdateAliasModel.setAddress(listAddress);
		apiCreateAndUpdateAliasModel.setDetail(listDetails);
		apiCreateAndUpdateAliasModel.setDescr(txtDesr.getValue());
		apiCreateAndUpdateAliasModel.setType(cmbType.getValue().getKey());
		if(apiReadFirewallAliasModel != null) {
			apiCreateAndUpdateAliasModel.setId(apiReadFirewallAliasModel.getName());
			updateAliases(apiCreateAndUpdateAliasModel);
		}else {
			createAliases(apiCreateAndUpdateAliasModel);
		}
	}
	
	private void createAliases(ApiCreateAndUpdateAliasModel apiCreateAndUpdateAliasModel) {
		try {
			ApiResultPfsenseResponse<Object> create = apiAliasService.createFirewallAlias(apiCreateAndUpdateAliasModel);
			if(create.isSuccess()) {
				fireEvent(new ClickEvent(this, false));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void updateAliases(ApiCreateAndUpdateAliasModel apiCreateAndUpdateAliasModel) {
		try {
			ApiResultPfsenseResponse<Object> update = apiAliasService.updateFirewallAlias(apiCreateAndUpdateAliasModel);
			if(update.isSuccess()) {
				fireEvent(new ClickEvent(this, false));
			}
		} catch (Exception e) {
		}
	}
}
