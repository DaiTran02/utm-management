package com.ngn.utm.manager.api.real_tech.log.create_group;

import lombok.Data;

@Data
public class ApiExportLogCreateGroupModel {
	private String fileName;
    private String fileType;
    private long fileSize;
    private byte[] fileData;
}
