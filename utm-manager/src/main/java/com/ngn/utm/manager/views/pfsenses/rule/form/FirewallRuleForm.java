package com.ngn.utm.manager.views.pfsenses.rule.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.vaadin.lineawesome.LineAwesomeIconUrl;

import com.ngn.utm.manager.api.pfsenses.ApiResultPfsenseResponse;
import com.ngn.utm.manager.api.pfsenses.rule.ApiExistingFirewallRuleModel;
import com.ngn.utm.manager.api.pfsenses.rule.ApiFirewallRuleService;
import com.ngn.utm.manager.api.pfsenses.utm_interface.ApiInterfaceService;
import com.ngn.utm.manager.api.pfsenses.utm_interface.ApiStatusInterfaceModel;
import com.ngn.utm.manager.service.FormInterface;
import com.ngn.utm.manager.utils.commons.ButtonTemplate;
import com.ngn.utm.manager.utils.commons.CantConnectToPfsenseForm;
import com.ngn.utm.manager.utils.commons.DialogTemplate;
import com.ngn.utm.manager.utils.commons.NotificationTemplate;
import com.ngn.utm.manager.utils.commons.VerticalLayoutTemplate;
import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.JsonElement;
import com.nimbusds.jose.shaded.gson.JsonObject;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class FirewallRuleForm extends VerticalLayoutTemplate implements FormInterface{
	private static final long serialVersionUID = 1L;
	private final ApiFirewallRuleService apiFirewallRuleService;
	private final ApiInterfaceService apiInterfaceService;
	
	private boolean isConnect = false;
	
	private Grid<ApiExistingFirewallRuleModel> grid = new Grid<ApiExistingFirewallRuleModel>(ApiExistingFirewallRuleModel.class,false);
	private List<ApiExistingFirewallRuleModel> listModel = new ArrayList<ApiExistingFirewallRuleModel>();
	
	private Map<String, List<ApiExistingFirewallRuleModel>> mapData = new HashMap<>();
	private List<ApiStatusInterfaceModel> listInterfaces = new ArrayList<ApiStatusInterfaceModel>();

	public FirewallRuleForm(ApiFirewallRuleService apiFirewallRuleService,ApiInterfaceService apiInterfaceService) {
		this.apiFirewallRuleService = apiFirewallRuleService;
		this.apiInterfaceService = apiInterfaceService;
		buildLayout();
		configComponent();
		loadData();
	}
	
	@Override
	public void buildLayout() {
		this.setSizeFull();
		
	}

	@Override
	public void configComponent() {
		
	}
	
	public void loadData() {
		listModel = new ArrayList<ApiExistingFirewallRuleModel>();
		listInterfaces = new ArrayList<ApiStatusInterfaceModel>();
		mapData.clear();
			try {
				ApiResultPfsenseResponse<List<JsonObject>> data = apiFirewallRuleService.readExistingFirewallRules();
				if(data.isSuccess()) {
					isConnect = true;
					Gson gson = new Gson();
					for(JsonElement element : data.getData()) {
						ApiExistingFirewallRuleModel firewallRuleModel = gson.fromJson(element, ApiExistingFirewallRuleModel.class);
						JsonObject jsonObject = element.getAsJsonObject();
						firewallRuleModel.setMyinterface(jsonObject.get("interface").getAsString());
						listModel.add(firewallRuleModel);
					}
					
					for(ApiExistingFirewallRuleModel firewallRuleModel :  listModel) {
						addValue(mapData, firewallRuleModel.getMyinterface(), firewallRuleModel);
					}
				}
				
			} catch (Exception e) {
				isConnect = false;
				this.removeAll();
				CantConnectToPfsenseForm cantConnectToPfsenseForm = new CantConnectToPfsenseForm();
				this.add(cantConnectToPfsenseForm);
				e.printStackTrace();
			}
			
			if(isConnect == true) {
				try {
					listInterfaces = apiInterfaceService.readInterfaceStatus().getData();
				} catch (Exception e) {
				}
			}

	}
	
	public void loadNewData(List<ApiExistingFirewallRuleModel> listData) {
		
	}
	
	public Component createLayout(String name,List<ApiExistingFirewallRuleModel> listData) {
		VerticalLayout vLayoutComponent = new VerticalLayout();
		vLayoutComponent.setSizeFull();
		
		ButtonTemplate btnAddNewRule = new ButtonTemplate("Add new rule",new SvgIcon(LineAwesomeIconUrl.PLUS_SOLID));
		btnAddNewRule.addClickListener(e->{
			openDialogAddNewRule();
		});
		
		
		vLayoutComponent.add(btnAddNewRule);
		
		Component component = createGrid();
		vLayoutComponent.add(component);
		grid.setItems(listData);
		
		return vLayoutComponent;
	}
	
	private Component createGrid() {
		grid = new Grid<ApiExistingFirewallRuleModel>(ApiExistingFirewallRuleModel.class,false);
		grid.addComponentColumn(model->{
			VerticalLayout vLayoutProtocol = new VerticalLayout();
			
			Span spHeader = new Span(model.getIpprotocol());
			
			vLayoutProtocol.add(spHeader);
			
			if(model.getIcmptype() != null) {
				Span spIcmp = new Span(model.getIcmptype());
				vLayoutProtocol.add(spIcmp);
			}
			
			return vLayoutProtocol;
		}).setHeader("Protocol");
		grid.addComponentColumn(model->{

			String strings = model.getSource().toString();

			int startIndex = strings.indexOf('=');
			
			String result = strings.substring(startIndex + 1, strings.length() - 1);
			
			if(result.isBlank()) {
				result = "*";
			}
			return new Text(result);
		}).setHeader("Source");

		grid.addComponentColumn(model->{
			if(model.getDestination().getPort()!=null) {
				return new Text(model.getDestination().getPort());
			}else {
				return new Text("*");
			}
		}).setHeader("Port");

		grid.addComponentColumn(model->{
			if(!(model.getDestination().getNetwork()==null)) {
				if(model.getDestination().getNetwork().equals("(self)")) {
					return new Text("This firewall");
				}
				return new Text(model.getDestination().getNetwork());
			}else {
				return new Text("*");
			}
		}).setHeader("Destination");
		grid.addComponentColumn(model->{
			if(!(model.getDescr()==null)){
				return new Text(model.getDescr());
			}else {
				return new Text("None");
			}
		}).setHeader("Description");
		
		grid.setSizeFull();
		
		return grid;
	}
	
	
	private static void addValue(Map<String, List<ApiExistingFirewallRuleModel>> map,String key,ApiExistingFirewallRuleModel value) {
		map.computeIfAbsent(key, k-> new ArrayList<ApiExistingFirewallRuleModel>()).add(value);
	}

	public Map<String, List<ApiExistingFirewallRuleModel>> getMapData() {
		return mapData;
	}

	public List<ApiStatusInterfaceModel> getListInterfaces() {
		return listInterfaces;
	}
	
	private void openDialogAddNewRule() {
		DialogTemplate dialogTemplate = new DialogTemplate("Add new rule");
		
		FirewallRuleEditForm firewallRuleEditForm = new FirewallRuleEditForm(apiFirewallRuleService, apiInterfaceService);
		firewallRuleEditForm.addChangeListener(e->{
			loadData();
			dialogTemplate.close();
			NotificationTemplate.success("Thành công");
		});
		dialogTemplate.add(firewallRuleEditForm);
		
		dialogTemplate.open();
		dialogTemplate.setHeightFull();
		dialogTemplate.setWidth("70%");
		dialogTemplate.getFooter().removeAll();
	}
}
