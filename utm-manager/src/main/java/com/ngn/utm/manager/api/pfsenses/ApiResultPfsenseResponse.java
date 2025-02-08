package com.ngn.utm.manager.api.pfsenses;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ApiResultPfsenseResponse <T>{
	private String status;
	private int code;
	@JsonProperty("return")
	private String returnpfsense;
	private String message;
	private T data;
	
	public boolean isSuccess() {
		return code == 200 ? true : false;
	}
}
