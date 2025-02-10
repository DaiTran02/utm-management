package com.ngn.utm.manager.api.real_tech.log.connectivity;

import lombok.Data;

@Data
public class ApiExportLogConnectivityModel {
	private String fileName;
    private String fileType;
    private long fileSize;
    private byte[] fileData;
}
