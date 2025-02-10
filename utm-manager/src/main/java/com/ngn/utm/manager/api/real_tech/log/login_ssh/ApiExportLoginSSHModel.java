package com.ngn.utm.manager.api.real_tech.log.login_ssh;

import lombok.Data;

@Data
public class ApiExportLoginSSHModel {
	private String fileName;
    private String fileType;
    private int fileSize;
    private byte[] fileData;
}
