package com.ngn.utm.manager.views.pfsenses.alias.form.type.model;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TypeNetworkModel {
	private TextField txtip;
	private ComboBox<Integer> cmbPort;
	private TextField txtDesr;
}
