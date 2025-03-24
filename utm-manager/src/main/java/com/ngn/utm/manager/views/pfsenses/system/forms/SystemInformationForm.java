package com.ngn.utm.manager.views.pfsenses.system.forms;

import java.util.ArrayList;
import java.util.List;

import com.ngn.utm.manager.api.pfsenses.system.ApiReadSystemHostNameModel;
import com.ngn.utm.manager.api.pfsenses.system.ApiReadSystemStatusModel;
import com.ngn.utm.manager.api.pfsenses.system.ApiReadSystemVersionModel;
import com.ngn.utm.manager.service.FormInterface;
import com.ngn.utm.manager.utils.commons.VerticalLayoutTemplate;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class SystemInformationForm extends VerticalLayoutTemplate implements FormInterface{
	private static final long serialVersionUID = 1L;
	private VerticalLayout vLayout = new VerticalLayout();

	private ApiReadSystemStatusModel apiReadSystemStatusModel;
	private ApiReadSystemHostNameModel apiReadSystemHostNameModel;
	private ApiReadSystemVersionModel apiSystemVersionModel;
	public SystemInformationForm(ApiReadSystemStatusModel apiReadSystemStatusModel,
			ApiReadSystemHostNameModel apiReadSystemHostNameModel, 
			ApiReadSystemVersionModel apiSystemVersionModel) {
		this.apiReadSystemStatusModel = apiReadSystemStatusModel;
		this.apiReadSystemHostNameModel = apiReadSystemHostNameModel;
		this.apiSystemVersionModel = apiSystemVersionModel;
		buildLayout();
		configComponent();
	}

	@Override
	public void buildLayout() {
		this.setPadding(false);
		this.setSizeFull();
		this.add(vLayout);
		createLayout();
		vLayout.setPadding(false);
	}

	@Override
	public void configComponent() {

	}

	private void createLayout() {
		vLayout.removeAll();
		String width = "100px";
		
		String widthChild = "100px";
		
		List<Component> listValueBios = new ArrayList<Component>();
		listValueBios.add(createKeyValue("Vendor: ", apiReadSystemStatusModel.getBios_vendor(), widthChild, true));
		listValueBios.add(createKeyValue("Version: ", apiReadSystemStatusModel.getBios_version(), widthChild, true));
		listValueBios.add(createKeyValue("Release Date: ", apiReadSystemStatusModel.getBios_date(), widthChild, false));
		Component componentBIOS = createKeyAndManyValue("BIOS: ", listValueBios, width);
		
		List<Component> listValueVersion = new ArrayList<Component>();
		listValueVersion.add(createKeyValue("", apiSystemVersionModel.getVersion(), widthChild, true));
		listValueVersion.add(createKeyValue("Built on: ", apiSystemVersionModel.getBuildtime(), widthChild, true));
		listValueVersion.add(createKeyValue("FreeBSD: ", apiSystemVersionModel.getBase(), widthChild, false));
		Component componentVersion = createKeyAndManyValue("Version: ", listValueVersion, widthChild);
		
		H5 headerHardware = new H5("Hardware crypto");
		headerHardware.getStyle().setMargin("auto");
		
		vLayout.add(createKeyValue("Name: ", apiReadSystemHostNameModel.getHostname()+"."+apiReadSystemHostNameModel.getDomain(), width, true),
				createKeyValue("System:", apiReadSystemStatusModel.getSystem_platform(), width, true),
				componentBIOS,componentVersion,
				createKeyValue("CPU Type: ", apiReadSystemStatusModel.getCpu_model(), width, true),headerHardware, new Hr(),
				createKeyValue("Kernrl PTI: ", apiReadSystemStatusModel.isKernel_pti() == false ? "Enabled" : "Disabled", width, true),
				createKeyValue("MDS Mitigation: ", apiReadSystemStatusModel.getMds_mitigation(), width, true));
		
		
		// Disk used
		Div divDiskUsage = new Div();
		ProgressBar progressBarDiskUsage = new ProgressBar();
		progressBarDiskUsage.setValue(apiReadSystemStatusModel.getDisk_usage());
		progressBarDiskUsage.setHeight("10px");
		
		Span progressBarSubLabelDiskUsage = new Span(apiReadSystemStatusModel.getDisk_usage()*100+"%");
		progressBarSubLabelDiskUsage.setId("disk");
		progressBarSubLabelDiskUsage.addClassNames(LumoUtility.TextColor.SECONDARY);
		
		progressBarDiskUsage.getElement().setAttribute("aria-describedby", "disk");
		
		divDiskUsage.setWidthFull();
		divDiskUsage.add(progressBarDiskUsage,progressBarSubLabelDiskUsage);
		
		
		vLayout.add(createKeyComponent("Disk usage: ", divDiskUsage, width, true));
		
		
		// Swap Use
		Div divSwapUsage = new Div();
		ProgressBar progressBarSwapUsage = new ProgressBar();
		progressBarSwapUsage.setValue(0);
		progressBarSwapUsage.setHeight("10px");
		
		Span progressBarSubLabelSwap = new Span(apiReadSystemStatusModel.getSwap_usage()+"% of 1024");
		progressBarSubLabelSwap.setId("sublbl");
		progressBarSubLabelSwap.addClassNames(LumoUtility.TextColor.SECONDARY);
		
		progressBarSwapUsage.getElement().setAttribute("aria-describedby", "sublbl");
		
		divSwapUsage.setWidthFull();
		divSwapUsage.add(progressBarSwapUsage,progressBarSubLabelSwap);
		
		vLayout.add(createKeyComponent("SWAP usage: ", divSwapUsage, width, true));
		
		//MBUF
		Div divMBUFUsage = new Div();
		ProgressBar progressBarMBUF = new ProgressBar();
		progressBarMBUF.setValue(apiReadSystemStatusModel.getMbuf_usage());
		progressBarMBUF.setHeight("10px");
		
		Span progressBarSubLabelMBUF = new Span(apiReadSystemStatusModel.getMbuf_usage()*100+"%");
		progressBarSubLabelMBUF.setId("mbuf");
		progressBarSubLabelMBUF.addClassNames(LumoUtility.TextColor.SECONDARY);
		
		progressBarMBUF.getElement().setAttribute("aria-describedby","mbuf");
		
		divMBUFUsage.setWidthFull();
		divMBUFUsage.add(progressBarMBUF,progressBarSubLabelMBUF);
		
		vLayout.add(createKeyComponent("MBUF usage: ", divMBUFUsage, width, true));
	}
	
	
	private Component createKeyComponent(String name,Component component,String width,boolean isBorder) {
		HorizontalLayout hLayoutKeyValue = new HorizontalLayout();

		H5 header = new H5(name);
		header.getStyle().setMargin("auto 0");
		header.setWidth(width);
		header.getStyle().setFlexShrink("0");


		hLayoutKeyValue.add(header,component);
		if(isBorder) {
			hLayoutKeyValue.getStyle().setBorderBottom("1px solid rgb(214 214 216)");
		}
		hLayoutKeyValue.setWidthFull();
		

		return hLayoutKeyValue;
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

	private Component createKeyAndManyValue(String name,List<Component> listComponent,String width) {
		HorizontalLayout hLayoutKeyAndManyValue = new HorizontalLayout();

		H5 header = new H5(name);
		header.setWidth(width);
		header.getStyle().setFlexShrink("0");
		header.getStyle().setMargin("auto 0");
		
		VerticalLayout vLayoutValue = new VerticalLayout();
		vLayoutValue.setPadding(false);
		
		listComponent.forEach(model->{
			vLayoutValue.add(model);
		});
		
		hLayoutKeyAndManyValue.setWidthFull();
		hLayoutKeyAndManyValue.add(header,vLayoutValue);
		hLayoutKeyAndManyValue.getStyle().setBorderBottom("1px solid rgb(214 214 216)");
		
		return hLayoutKeyAndManyValue;

	}

}
