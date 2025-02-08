package com.ngn.utm.manager.views.realteach.host.form;

import com.ngn.utm.manager.api.ApiResultResponse;
import com.ngn.utm.manager.api.real_tech.host.ApiHostModel;
import com.ngn.utm.manager.api.real_tech.host.ApiHostService;
import com.ngn.utm.manager.service.FormInterface;
import com.ngn.utm.manager.utils.commons.NotificationTemplate;
import com.ngn.utm.manager.utils.commons.VerticalLayoutTemplate;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

public class HostEditForm extends VerticalLayoutTemplate implements FormInterface{
	private static final long serialVersionUID = 1L;
	private final ApiHostService apiHostService;
	
	private VerticalLayout vLayout = new VerticalLayout();
	private TextField txtNameHost = new TextField("Tên");
	private TextField txtIp = new TextField("Địa chỉ IP");
	private TextField txtMnPort = new TextField("Port");
	private TextField txtClientId = new TextField("ClientID");
	private PasswordField txtClientToken = new PasswordField("ClientToken");
	private Checkbox cbUseConfigModule = new Checkbox("Use config module");
	private Checkbox cbUseLogModule = new Checkbox("Use log module");
	
	private ApiHostModel apiHostModel = new ApiHostModel();
	private String idHost;
	public HostEditForm(ApiHostService apiHostService,String idHost) {
		this.apiHostService = apiHostService;
		buildLayout();
		configComponent();
		if(idHost != null) {
			this.idHost = idHost;
			loadData();
		}
	}
	
	@Override
	public void buildLayout() {
		this.setSizeFull();
		this.add(vLayout);
		createLayout();
	}

	@Override
	public void configComponent() {
		
	}
	
	private void loadData() {
		apiHostModel = new ApiHostModel();
		ApiResultResponse<ApiHostModel> data = apiHostService.getOneHost(idHost);
		if(data.isSuccess()) {
			apiHostModel = data.getData();
			txtNameHost.setValue(apiHostModel.getHostname());
			txtMnPort.setValue(String.valueOf(apiHostModel.getMntPort()));
			txtClientId.setValue(apiHostModel.getClientId());
			txtClientToken.setValue(apiHostModel.getClientToken());
			txtIp.setValue(apiHostModel.getIpAddress());
			cbUseConfigModule.setValue(apiHostModel.isUseConfigModule());
			cbUseLogModule.setValue(apiHostModel.isUseLogModule());
		}
	}
	
	private void createLayout() {
		vLayout.add(txtNameHost);
		txtNameHost.setWidthFull();
		
		HorizontalLayout hLayout1 = new HorizontalLayout();
		hLayout1.setWidthFull();
		txtIp.setWidth("70%");
		txtMnPort.setWidth("30%");
		hLayout1.add(txtIp,txtMnPort);	
		
		HorizontalLayout hLayout2 = new HorizontalLayout();
		hLayout2.setWidthFull();
		txtClientId.setWidth("30%");
		txtClientToken.setWidth("70%");
		hLayout2.add(txtClientId,txtClientToken);
		
		HorizontalLayout hLayout3 = new HorizontalLayout();
		hLayout3.setWidthFull();
		hLayout3.add(cbUseConfigModule,cbUseLogModule);
		
		vLayout.add(hLayout1,hLayout2,hLayout3);
	}
	
	public void saveHost() {
		if(invalid() == false) {
			return;
		}
		ApiHostModel apiHostModel = new ApiHostModel();
		apiHostModel.setClientId(txtClientId.getValue());
		apiHostModel.setClientToken(txtClientToken.getValue());
		apiHostModel.setHostname(txtNameHost.getValue());
		apiHostModel.setIpAddress(txtIp.getValue());
		apiHostModel.setMntPort(Integer.valueOf(txtMnPort.getValue()));
		apiHostModel.setUseConfigModule(cbUseConfigModule.getValue());
		apiHostModel.setUseLogModule(cbUseLogModule.getValue());
		
		if(idHost != null) {
			doUpdate(apiHostModel);
		}else {
			doCreate(apiHostModel);
		}
		
	}
	
	private void doCreate(ApiHostModel apiHostModel) {
		ApiResultResponse<Object> create = apiHostService.createHost(apiHostModel);
		if(create.isSuccess()) {
			fireEvent(new ClickEvent(this, false));
		}else {
			NotificationTemplate.warning(create.getMessage());
		}
	}
	
	private void doUpdate(ApiHostModel apiHostModel) {
		ApiResultResponse<Object> update = apiHostService.updateHost(idHost,apiHostModel);
		if(update.isSuccess()) {
			fireEvent(new ClickEvent(this, false));
		}
	}
	
	private boolean invalid() {
		if(txtClientId.getValue().isEmpty()) {
			txtClientId.setErrorMessage("Không được bỏ trống");
			txtClientId.setInvalid(true);
			return false;
		}
		
		if(txtClientToken.getValue().isEmpty()) {
			txtClientToken.setErrorMessage("Không được bỏ trống");
			txtClientToken.setInvalid(true);
			return false;
		}
		
		if(txtNameHost.getValue().isEmpty()) {
			txtNameHost.setErrorMessage("Không được bỏ trống");
			txtNameHost.setInvalid(true);
			return false;
		}
		
		if(txtIp.getValue().isEmpty()) {
			txtIp.setErrorMessage("Không được bỏ trống");
			txtIp.setInvalid(true);
			return false;
		}
		
		
		return true;
	}

}
