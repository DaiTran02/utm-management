package com.ngn.utm.manager.views.pfsenses.alias.form.type;

import java.util.List;

import com.ngn.utm.manager.utils.commons.VerticalLayoutTemplate;
import com.ngn.utm.manager.views.pfsenses.alias.form.type.model.TypeGeneralModel;

public abstract class TypeBaseForm extends VerticalLayoutTemplate{
	private static final long serialVersionUID = 1L;
	
	public abstract void loadData(List<TypeGeneralModel> list);

}
