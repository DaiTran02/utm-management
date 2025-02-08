package com.ngn.utm.manager.views.pfsenses.service.form;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.lineawesome.LineAwesomeIconUrl;

import com.ngn.utm.manager.api.pfsenses.ApiResultPfsenseResponse;
import com.ngn.utm.manager.api.pfsenses.service.ApiServiceOfUtmModel;
import com.ngn.utm.manager.api.pfsenses.service.ApiServiceOfUtmService;
import com.ngn.utm.manager.service.FormInterface;
import com.ngn.utm.manager.utils.SessionUtil;
import com.ngn.utm.manager.utils.commons.ButtonTemplate;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class ServiceForm extends VerticalLayout implements FormInterface{
	private static final long serialVersionUID = 1L;
	private ApiServiceOfUtmService apiServiceOfUtmService;
	private boolean isConnect = SessionUtil.getDeviceInfo().isConnect();
	
	private ButtonTemplate btnStartAll = new ButtonTemplate("Start All",new SvgIcon(LineAwesomeIconUrl.PLAY_SOLID));
	private ButtonTemplate btnStopAll = new ButtonTemplate("Stop All",new SvgIcon(LineAwesomeIconUrl.STOP_SOLID));
	
	private Grid<ApiServiceOfUtmModel> grid = new Grid<ApiServiceOfUtmModel>(ApiServiceOfUtmModel.class,false);
	private List<ApiServiceOfUtmModel> listModel = new ArrayList<ApiServiceOfUtmModel>();
	
	public ServiceForm(ApiServiceOfUtmService apiServiceOfUtmService) {
		this.apiServiceOfUtmService = apiServiceOfUtmService;
		buildLayout();
		configComponent();
		loadData();
	}
	
	@Override
	public void buildLayout() {
		this.setSizeFull();
		HorizontalLayout hLayoutButton = new HorizontalLayout();
		hLayoutButton.add(btnStartAll,btnStopAll);
		
		btnStartAll.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
		btnStopAll.addThemeVariants(ButtonVariant.LUMO_ERROR);
		
		this.add(hLayoutButton);
		this.add(createGrid());
		
	}

	@Override
	public void configComponent() {
		
	}
	
	private void loadData() {
		listModel = new ArrayList<ApiServiceOfUtmModel>();
		if(isConnect) {
			try {
				ApiResultPfsenseResponse<List<ApiServiceOfUtmModel>> data = apiServiceOfUtmService.readAllServiceStatues();
				listModel.addAll(data.getData());
			} catch (Exception e) {
			}
			
			grid.setItems(listModel);
			
		}else {
			
		}
	}
	
	private Component createGrid() {
		grid = new Grid<ApiServiceOfUtmModel>(ApiServiceOfUtmModel.class,false);
		
		grid.addColumn(ApiServiceOfUtmModel::getName).setHeader("Service");
		grid.addColumn(ApiServiceOfUtmModel::getDescription).setHeader("Description");
		grid.addColumn(ApiServiceOfUtmModel::getStatus).setHeader("Status");
		
		return grid;
	}

}
