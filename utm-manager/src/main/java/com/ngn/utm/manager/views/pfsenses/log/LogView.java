package com.ngn.utm.manager.views.pfsenses.log;

import com.ngn.utm.manager.api.pfsenses.logs.ApiLogService;
import com.ngn.utm.manager.views.MainLayout;
import com.ngn.utm.manager.views.pfsenses.log.form.FirewallLogForm;
import com.ngn.utm.manager.views.pfsenses.log.form.SystemLogForm;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;

@Route(value = "utm_log", layout = MainLayout.class)
@PermitAll
@PageTitle("System logs")
public class LogView extends TabSheet{
	private static final long serialVersionUID = 1L;
	
	public LogView(ApiLogService apiLogService) {
		this.setSizeFull();
		SystemLogForm systemLogForm = new SystemLogForm(apiLogService);
		this.add("System", systemLogForm);
		
		FirewallLogForm firewallLogForm = new FirewallLogForm(apiLogService);
		this.add("Firewall", firewallLogForm);
		
		this.addSelectedChangeListener(e->{
			if(this.getSelectedIndex() == 1) {
				firewallLogForm.loadData();
			}
		});
		
	}
	

}
