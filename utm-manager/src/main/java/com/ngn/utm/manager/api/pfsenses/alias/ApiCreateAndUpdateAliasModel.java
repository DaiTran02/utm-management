package com.ngn.utm.manager.api.pfsenses.alias;

import java.util.List;

import lombok.Data;

@Data
public class ApiCreateAndUpdateAliasModel {
	private List<String> address;
	private boolean apply;
	private String descr;
	private List<String> detail;
	private String name;
	private String type;
	private String id;
}
