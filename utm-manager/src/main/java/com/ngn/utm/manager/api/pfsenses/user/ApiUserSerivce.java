package com.ngn.utm.manager.api.pfsenses.user;

import java.util.List;

import com.ngn.utm.manager.api.pfsenses.ApiResultPfsenseResponse;

public interface ApiUserSerivce {
	ApiResultPfsenseResponse<List<ApiUtmUserModel>> readUsers() throws Exception;
	ApiResultPfsenseResponse<Object> createUser(ApiUtmUserInputModel apiUtmUserModel) throws Exception;
	ApiResultPfsenseResponse<Object> updateUser(ApiUtmUserInputModel apiUtmUserModel) throws Exception;
	ApiResultPfsenseResponse<Object> deleteUser(String idUser) throws Exception;
}
