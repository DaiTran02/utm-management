package com.ngn.utm.manager.api.real_tech.log.login_ssh;

import com.ngn.utm.manager.api.ApiResultResponse;

public interface ApiLoginSSHSerivce {
	ApiResultResponse<ApiDataLoginSSHModel> getLogLoginSSHByFilter(ApiFilterLoginSSHModel apiFilterLoginSSHModel);
	ApiResultResponse<ApiLoginSSHModel> getLoginSSHById(String id);
	ApiResultResponse<ApiExportLoginSSHModel> exportLoginSSHByFilter(ApiFilterLoginSSHModel apiFilterLoginSSHModel);
}
