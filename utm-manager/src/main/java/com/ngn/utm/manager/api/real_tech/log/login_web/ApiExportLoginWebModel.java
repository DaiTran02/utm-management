package com.ngn.utm.manager.api.real_tech.log.login_web;

import lombok.Data;

@Data
public class ApiExportLoginWebModel {
	private String fileName;
    private String fileType;
    private long fileSize;
    private byte[] fileData;
}
