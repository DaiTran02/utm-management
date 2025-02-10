package com.ngn.utm.manager.api.real_tech.log.ips;

import lombok.Data;

@Data
public class ApiExportLogIpsModel {
	private String fileName;
    private String fileType;
    private long fileSize;
    private byte[] fileData;
}
