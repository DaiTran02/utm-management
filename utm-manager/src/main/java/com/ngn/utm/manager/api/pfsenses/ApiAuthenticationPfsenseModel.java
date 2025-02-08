package com.ngn.utm.manager.api.pfsenses;

import com.ngn.utm.manager.api.real_tech.host.ApiHostModel;

import lombok.Data;

@Data
public class ApiAuthenticationPfsenseModel {
	private String clientId;
	private String clientToken;
	
	public ApiAuthenticationPfsenseModel(ApiHostModel apiHostModel){
		this.clientId = apiHostModel.getClientId();
		this.clientToken = apiHostModel.getClientToken();
	}
	
}
