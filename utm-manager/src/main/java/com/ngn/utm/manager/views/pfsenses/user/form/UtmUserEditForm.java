package com.ngn.utm.manager.views.pfsenses.user.form;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;

import org.vaadin.lineawesome.LineAwesomeIconUrl;

import com.ngn.utm.manager.api.pfsenses.ApiResultPfsenseResponse;
import com.ngn.utm.manager.api.pfsenses.user.ApiUserSerivce;
import com.ngn.utm.manager.api.pfsenses.user.ApiUtmUserInputModel;
import com.ngn.utm.manager.api.pfsenses.user.ApiUtmUserModel;
import com.ngn.utm.manager.service.FormInterface;
import com.ngn.utm.manager.utils.LocalDateUtil;
import com.ngn.utm.manager.utils.commons.ButtonTemplate;
import com.ngn.utm.manager.utils.commons.HeaderComponent;
import com.ngn.utm.manager.utils.commons.NotificationTemplate;
import com.ngn.utm.manager.utils.commons.VerticalLayoutTemplate;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

public class UtmUserEditForm extends VerticalLayoutTemplate implements FormInterface{
	private static final long serialVersionUID = 1L;
	private ApiUserSerivce apiUserSerivce;


	private VerticalLayout vLayout = new VerticalLayout();
	private Checkbox cbUserCannotLogin = new Checkbox("This user cannot login");
	private TextField txtUsername = new TextField("Username");
	private PasswordField txtPassword = new PasswordField("Password");
	private PasswordField txtConfirmPassword = new PasswordField("Confirm Password");
	private TextField txtFullname = new TextField("Full name");
	private DatePicker dateExpirationDate = new DatePicker("Expiration date");
	private TextArea txtAuthorizedSSHKeys = new TextArea("Authorized SSH Keys");
	private TextField txtIpSecPreSharedKey = new TextField("IPsec Pre-Shared Key");

	private ButtonTemplate btnSave = new ButtonTemplate("Save",new SvgIcon(LineAwesomeIconUrl.SAVE_SOLID));

	private ApiUtmUserModel apiUtmUserModel;
	public UtmUserEditForm(ApiUserSerivce apiUserSerivce,ApiUtmUserModel apiUtmUserModel) {
		this.apiUserSerivce = apiUserSerivce;
		this.apiUtmUserModel = apiUtmUserModel;
		buildLayout();
		configComponent();
		loadData();
	}

	@Override
	public void buildLayout() {
		this.setSizeFull();
		this.add(vLayout);
		createLayout();

	}

	@Override
	public void configComponent() {
		btnSave.addClickListener(e->{
			save();
		});

	}

	private void loadData() {
		if(apiUtmUserModel != null) {
			txtUsername.setValue(apiUtmUserModel.getName());
			txtUsername.setReadOnly(true);
			txtFullname.setValue(apiUtmUserModel.getDescr());
			txtAuthorizedSSHKeys.setValue(apiUtmUserModel.getAuthorizedkeys());
			txtIpSecPreSharedKey.setValue(apiUtmUserModel.getIpsecpsk());
			cbUserCannotLogin.setValue(apiUtmUserModel.getDisabled() == null ? false : true);

			if(apiUtmUserModel.getExpires() != null || !apiUtmUserModel.getExpires().isEmpty()) {
				
				String date = apiUtmUserModel.getExpires();
				String newDate = date.replace("/", "-");
				
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
				
				try {
					 LocalDate localDate = LocalDate.parse(newDate, formatter);
					dateExpirationDate.setValue(localDate);
				} catch (DateTimeParseException e) {
				}
			}
		}	
	}

	private void createLayout() {
		vLayout.removeAll();

		//User Properties
		HeaderComponent headerUserProperties = new HeaderComponent("User Properties");
		String widthUserProperties = "100px";
		VerticalLayout vLayoutUserProperties = new VerticalLayout();
		vLayoutUserProperties.setWidthFull();
		vLayoutUserProperties.add(createKeyValue("Defined by: ", "USER", widthUserProperties, true));

		HorizontalLayout hLayoutPassword = new HorizontalLayout();
		hLayoutPassword.setWidthFull();
		txtUsername.setWidthFull();
		txtPassword.setWidthFull();
		txtConfirmPassword.setWidthFull();
		txtFullname.setWidthFull();
		txtFullname.setHelperText("User's full name, for administrative information only");

		txtIpSecPreSharedKey.setWidthFull();
		txtAuthorizedSSHKeys.setWidthFull();
		dateExpirationDate.setWidth("50%");
		dateExpirationDate.setHelperText("Leave blank if the account shouldn't expire, otherwise enter the expiration date as MM/DD/YYYY");

		hLayoutPassword.add(txtPassword,txtConfirmPassword);

		vLayoutUserProperties.add(cbUserCannotLogin,txtUsername,hLayoutPassword,txtFullname,dateExpirationDate);
		headerUserProperties.addLayout(vLayoutUserProperties);

		//Keys
		HeaderComponent headerKeys = new HeaderComponent("Keys");
		VerticalLayout vLayoutKeys = new VerticalLayout();
		vLayoutKeys.setWidthFull();
		txtAuthorizedSSHKeys.setHelperText("Enter authorized SSH keys for this user");
		vLayoutKeys.add(txtAuthorizedSSHKeys,txtIpSecPreSharedKey);

		headerKeys.addLayout(vLayoutKeys);

		btnSave.getStyle().setMarginLeft("auto");

		vLayout.add(headerUserProperties,headerKeys,btnSave);

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

	@SuppressWarnings("unused")
	private void save() {
		if(isValid()) {
			return;
		}
		ApiUtmUserInputModel apiUtmUserModel = new ApiUtmUserInputModel();
		apiUtmUserModel.setAuthorizedkeys(txtAuthorizedSSHKeys.getValue());
		apiUtmUserModel.setUsername(txtUsername.getValue());
		apiUtmUserModel.setDisabled(cbUserCannotLogin.getValue());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy");
		
		if(dateExpirationDate.getValue() != null) {
			try {
				String date = simpleDateFormat.format(LocalDateUtil.localDateToLong(dateExpirationDate.getValue()));
				String newDate = date.replace("-", "/");
				System.out.println(newDate);
				apiUtmUserModel.setExpires(newDate);
			} catch (Exception e) {
			}
		}
		
//		if(dateExpirationDate.getValue() != null && dateExpirationDate.getValue()) {
//			String date = simpleDateFormat.format(dateExpirationDate.getValue());
//			apiUtmUserModel.setExpires(date);
//		}
		apiUtmUserModel.setIpsecpsk(txtIpSecPreSharedKey.getValue());
		apiUtmUserModel.setDescr(txtFullname.getValue());
		apiUtmUserModel.setPassword(txtPassword.getValue());
		apiUtmUserModel.setCert(Collections.emptyList());
		apiUtmUserModel.setPriv(Collections.emptyList());


		if(apiUtmUserModel == null) {
			doCreateUser(apiUtmUserModel);
		}else {
			if(txtPassword.getValue() != null) {
				apiUtmUserModel.setPassword(txtPassword.getValue());
			}else {
				apiUtmUserModel.setPassword(null);
			}
			doUpdateUser(apiUtmUserModel);
		}
	}

	private void doCreateUser(ApiUtmUserInputModel apiUtmUserModel) {
		try {
			ApiResultPfsenseResponse<Object> createUser = apiUserSerivce.createUser(apiUtmUserModel);
			if(createUser.isSuccess()) {
				System.out.println("Data ne: "+createUser);
				NotificationTemplate.success("Thành công");
				fireEvent(new ClickEvent(this, false));
			}else {
				System.out.println("What: "+createUser);
				NotificationTemplate.error("Không thể tạo người dùng");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void doUpdateUser(ApiUtmUserInputModel apiUtmUserModel) {
		try {
			ApiResultPfsenseResponse<Object> updateUser = apiUserSerivce.updateUser(apiUtmUserModel);
			if(updateUser.isSuccess()) {
				NotificationTemplate.success("Thành công");
				fireEvent(new ClickEvent(this, false));
			}else {
				NotificationTemplate.warning("Không thể cập nhật");
				System.out.println(updateUser);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private boolean isValid() {
		if(txtUsername.isEmpty()) {
			txtUsername.setErrorMessage("Không được để trống");
			txtUsername.setInvalid(true);
			txtUsername.focus();
			return true;
		}

		if(!txtPassword.getValue().equals(txtConfirmPassword.getValue())) {
			txtConfirmPassword.setErrorMessage("Mật khẩu không trùng khớp");
			txtConfirmPassword.setInvalid(true);
			txtConfirmPassword.focus();
			return true;
		}


		return false;
	}

}
