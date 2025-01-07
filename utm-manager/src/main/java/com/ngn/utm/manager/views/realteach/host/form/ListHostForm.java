package com.ngn.utm.manager.views.realteach.host.form;

import java.util.List;

import com.ngn.utm.manager.api.ApiResultResponse;
import com.ngn.utm.manager.api.real_tech.host.ApiHostModel;
import com.ngn.utm.manager.api.real_tech.host.ApiHostService;
import com.ngn.utm.manager.service.FormInterface;
import com.ngn.utm.manager.utils.commons.VerticalLayoutTemplate;

public class ListHostForm extends VerticalLayoutTemplate implements FormInterface{
	private static final long serialVersionUID = 1L;
	private ApiHostService apiHostService;
	
	public ListHostForm(ApiHostService apiHostService) {
		this.apiHostService = apiHostService;
		buildLayout();
		configComponent();
		loadData();
	}
	
	@Override
	public void buildLayout() {
		
	}

	@Override
	public void configComponent() {
		
	}
	
	private void loadData() {
		ApiResultResponse<List<ApiHostModel>> data = apiHostService.getAllHost();
		System.out.println(data);
	}

}
