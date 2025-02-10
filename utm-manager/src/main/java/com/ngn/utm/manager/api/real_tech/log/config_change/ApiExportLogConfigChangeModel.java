package com.ngn.utm.manager.api.real_tech.log.config_change;

import lombok.Data;

@Data
public class ApiExportLogConfigChangeModel {
	private String fileName;
	private String fileType;
	private long fileSize;
	private byte[] fileData;
}
