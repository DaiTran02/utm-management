package com.ngn.utm.manager.api.real_tech.log.create_user;

import lombok.Data;

@Data
public class ApiExportLogCreateUserModel {
	private String fileName;
    private String fileType;
    private long fileSize;
    private byte[] fileData;
}
