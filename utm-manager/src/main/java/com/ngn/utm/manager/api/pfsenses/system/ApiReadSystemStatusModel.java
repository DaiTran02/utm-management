package com.ngn.utm.manager.api.pfsenses.system;

import java.util.List;

import lombok.Data;

@Data
public class ApiReadSystemStatusModel {
    private String system_platform;
    private String system_serial;
    private String system_netgate_id;
    private String bios_vendor;
    private String bios_version;
    private String bios_date;
    private String cpu_model;
    private boolean kernel_pti;
    private String mds_mitigation;
    private Object temp_c;
    private Object temp_f;
    private List<Double> load_avg;
    private int mbuf_usage;
    private double mem_usage;
    private int swap_usage;
    private double disk_usage;
}
