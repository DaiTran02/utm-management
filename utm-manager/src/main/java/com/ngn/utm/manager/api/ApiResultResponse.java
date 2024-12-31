package com.ngn.utm.manager.api;

import lombok.Data;

@Data
public class ApiResultResponse<T> {
	private int status;
	private String message;
	private T result = null;
	
	public boolean isSuccess() {
		if(status == 0) {
			return true;
		}
		return false;
	}
}
