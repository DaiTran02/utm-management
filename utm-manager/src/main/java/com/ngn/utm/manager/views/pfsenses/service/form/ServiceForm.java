package com.ngn.utm.manager.views.pfsenses.service.form;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.lineawesome.LineAwesomeIconUrl;

import com.ngn.utm.manager.api.pfsenses.ApiResultPfsenseResponse;
import com.ngn.utm.manager.api.pfsenses.service.ApiServiceOfUtmModel;
import com.ngn.utm.manager.api.pfsenses.service.ApiServiceOfUtmService;
import com.ngn.utm.manager.service.FormInterface;
import com.ngn.utm.manager.utils.commons.ButtonTemplate;
import com.ngn.utm.manager.utils.commons.CantConnectToPfsenseForm;
import com.ngn.utm.manager.utils.commons.ConfirmDialogTemplate;
import com.ngn.utm.manager.utils.commons.NotificationTemplate;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class ServiceForm extends VerticalLayout implements FormInterface{
	private static final long serialVersionUID = 1L;
	private ApiServiceOfUtmService apiServiceOfUtmService;
	
	private ButtonTemplate btnStartAll = new ButtonTemplate("Start All",new SvgIcon(LineAwesomeIconUrl.PLAY_SOLID));
	private ButtonTemplate btnStopAll = new ButtonTemplate("Stop All",new SvgIcon(LineAwesomeIconUrl.STOP_CIRCLE));
	
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
		btnStartAll.addClickListener(e->{
			doStartAll();
		});
		
		btnStopAll.addClickListener(e->{
			ConfirmDialogTemplate confirmDialogTemplate = new ConfirmDialogTemplate("Dừng tất cả service");
			confirmDialogTemplate.setText("Bạn có chắc muốn dừng tất cả");
			confirmDialogTemplate.addConfirmListener(ev->{
				doStopService();
			});
			confirmDialogTemplate.setCancelable(true);
			confirmDialogTemplate.open();
		});
	}
	
	private void loadData() {
		listModel = new ArrayList<ApiServiceOfUtmModel>();
			try {
				ApiResultPfsenseResponse<List<ApiServiceOfUtmModel>> data = apiServiceOfUtmService.readAllServiceStatues();
				listModel.addAll(data.getData());
			} catch (Exception e) {
				this.removeAll();
				CantConnectToPfsenseForm cantConnectToPfsenseForm = new CantConnectToPfsenseForm();
				this.add(cantConnectToPfsenseForm);
			}
			
			grid.setItems(listModel);
			

	}
	
	private void doStartAll() {
		try {
			ApiResultPfsenseResponse<Object> startAll = apiServiceOfUtmService.restartAllService();
			if(startAll.isSuccess()) {
				NotificationTemplate.success("Thành công");
				loadData();
			}else {
				System.out.println(startAll);
				NotificationTemplate.warning("Không thành công");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void doStopService() {
		try {
			ApiResultPfsenseResponse<Object> stopService = apiServiceOfUtmService.stopService();
			if(stopService.isSuccess()) {
				NotificationTemplate.success("Thành công");
				loadData();
			}else {
				NotificationTemplate.warning("Thất bại");
			}
		} catch (Exception e) {
		}
	}
	
	private void doRestart(ApiServiceOfUtmModel apiServiceOfUtmModel) {
		try {
			ApiResultPfsenseResponse<Object> restart = apiServiceOfUtmService.restartService(apiServiceOfUtmModel);
			if(restart.isSuccess()) {
				NotificationTemplate.success("Thành công");
				loadData();
			}else {
				NotificationTemplate.warning("không thể restart");
			}
		} catch (Exception e) {
		}
	}
	
	private Component createGrid() {
		grid = new Grid<ApiServiceOfUtmModel>(ApiServiceOfUtmModel.class,false);
		
		grid.addColumn(ApiServiceOfUtmModel::getName).setHeader("Service");
		grid.addColumn(ApiServiceOfUtmModel::getDescription).setHeader("Description");
		grid.addColumn(ApiServiceOfUtmModel::getStatus).setHeader("Status");
		grid.addComponentColumn(model->{
			HorizontalLayout hLayoutButton = new HorizontalLayout();
			ButtonTemplate btnRestart = new ButtonTemplate("Restart");
			btnRestart.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
			btnRestart.addClickListener(e->{
				doRestart(model);
			});
			
			
			
			hLayoutButton.add(btnRestart);
			
			return hLayoutButton;
		});
		
		return grid;
	}

}
