package com.ngn.utm.manager.views.pfsenses.utm_interface.form;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.lineawesome.LineAwesomeIconUrl;

import com.ngn.utm.manager.api.pfsenses.ApiResultPfsenseResponse;
import com.ngn.utm.manager.api.pfsenses.utm_interface.ApiInterfaceService;
import com.ngn.utm.manager.api.pfsenses.utm_interface.ApiStatusInterfaceModel;
import com.ngn.utm.manager.service.FormInterface;
import com.ngn.utm.manager.utils.SessionUtil;
import com.ngn.utm.manager.utils.commons.VerticalLayoutTemplate;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.SvgIcon;

public class StatusInterfaceForm extends VerticalLayoutTemplate implements FormInterface{
	private static final long serialVersionUID = 1L;
	private ApiInterfaceService apiInterfaceService;
	private boolean isConnect = SessionUtil.getDeviceInfo().isConnect();

	private Grid<ApiStatusInterfaceModel> grid = new Grid<ApiStatusInterfaceModel>(ApiStatusInterfaceModel.class,false);
	private List<ApiStatusInterfaceModel> listModel = new ArrayList<ApiStatusInterfaceModel>();


	public StatusInterfaceForm(ApiInterfaceService apiInterfaceService) {
		this.apiInterfaceService = apiInterfaceService;
		buildLayout();
		configComponent();
		loadData();
	}

	@Override
	public void buildLayout() {
		this.setWidthFull();
		this.add(createGrid());
	}

	@Override
	public void configComponent() {

	}

	private void loadData() {
		listModel = new ArrayList<ApiStatusInterfaceModel>();
		if(isConnect) {
			try {
				ApiResultPfsenseResponse<List<ApiStatusInterfaceModel>> data = apiInterfaceService.readInterfaceStatus();
				if(data.isSuccess()) {
					listModel.addAll(data.getData());
				}

				grid.setItems(listModel);

			} catch (Exception e) {
			}
		}else {

		}
	}

	private Component createGrid() {
		grid = new Grid<ApiStatusInterfaceModel>(ApiStatusInterfaceModel.class,false);

		grid.addColumn(ApiStatusInterfaceModel::getDescr);
		grid.addColumn(ApiStatusInterfaceModel::getMedia);
		grid.addColumn(ApiStatusInterfaceModel::getIpaddr);
		grid.addComponentColumn(model->{
			SvgIcon icon;

			if(model.getStatus().toLowerCase().equals("up")) {
				icon = new SvgIcon(LineAwesomeIconUrl.ARROW_ALT_CIRCLE_UP);
				icon.getElement().getThemeList().add("badge success");
			}else {
				icon = new SvgIcon(LineAwesomeIconUrl.ARROW_ALT_CIRCLE_DOWN);
				icon.getElement().getThemeList().add("badge error");
			}
			return icon;
		}).setWidth("80px").setFlexGrow(0);
		
		grid.setHeight("auto");
		grid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
		
		return grid;
	}

}
