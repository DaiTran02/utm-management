package com.ngn.utm.manager.views.pfsenses.log.form;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ngn.utm.manager.api.pfsenses.logs.ApiLogPfsenseModel;
import com.ngn.utm.manager.api.pfsenses.logs.ApiLogService;
import com.ngn.utm.manager.service.FormInterface;
import com.ngn.utm.manager.utils.ParsingStringUtil;
import com.ngn.utm.manager.utils.SessionUtil;
import com.ngn.utm.manager.utils.commons.CantConnectToPfsenseForm;
import com.ngn.utm.manager.utils.commons.VerticalLayoutTemplate;
import com.ngn.utm.manager.views.pfsenses.log.model.ModelParsingLog;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;

public class SystemLogForm extends VerticalLayoutTemplate implements FormInterface{
	private static final long serialVersionUID = 1L;
	private ApiLogService apiLogService;
	private boolean isConnect = SessionUtil.getDeviceInfo().isConnect();
	private List<ApiLogPfsenseModel> listModel = new ArrayList<ApiLogPfsenseModel>();
	
	private Grid<ModelParsingLog> grid = new Grid<ModelParsingLog>(ModelParsingLog.class,false);
	private List<ModelParsingLog> listDataForGrid = new ArrayList<ModelParsingLog>();
	
	public SystemLogForm(ApiLogService apiLogService) {
		this.apiLogService = apiLogService;
		buildLayout();
		configComponent();
		loadData();
	}
	
	@Override
	public void buildLayout() {
		this.setSizeFull();
		this.add(createGrid());
	}

	@Override
	public void configComponent() {
		
	}
	
	private void loadData() {
		listModel = new ArrayList<ApiLogPfsenseModel>();
		listDataForGrid = new ArrayList<ModelParsingLog>();
		if(isConnect) {
			try {
				for(String itemLog : apiLogService.readSystemLog().getData()) {
					ApiLogPfsenseModel log = new ApiLogPfsenseModel();
					log.setLogSystem(itemLog);
					listModel.add(log);
				}
			} catch (Exception e) {
			}
			
			for(ApiLogPfsenseModel logModel : listModel) {
				Optional<List<String>> data = ParsingStringUtil.parsingSystemLog(logModel.getLogSystem());
				if(data.isPresent()) {
					List<String> rowData = data.get();
					ModelParsingLog modelParsingLog = new ModelParsingLog();
					modelParsingLog.setSt1(rowData.get(1));
					modelParsingLog.setSt2(rowData.get(3));
					modelParsingLog.setSt3(rowData.get(4));
					modelParsingLog.setSt4(rowData.get(5));
					
					listDataForGrid.add(modelParsingLog);
				}
			}
		}else {
			this.removeAll();
			CantConnectToPfsenseForm cantConnectToPfsenseForm = new CantConnectToPfsenseForm();
			this.add(cantConnectToPfsenseForm);
		}
		
		grid.setItems(listDataForGrid);
	}
	
	private Component createGrid() {
		grid = new Grid<ModelParsingLog>(ModelParsingLog.class,false);
		
		grid.addColumn(ModelParsingLog::getSt1).setHeader("Time").setWidth("180px").setFlexGrow(0);
		grid.addColumn(ModelParsingLog::getSt2).setHeader("Process").setWidth("150px").setFlexGrow(0);
		grid.addColumn(ModelParsingLog::getSt3).setHeader("IPD").setWidth("90px").setFlexGrow(0);
		grid.addColumn(ModelParsingLog::getSt4).setHeader("Message");
		
		grid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
		
		return grid;
	}
	

}
