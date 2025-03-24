package com.ngn.utm.manager.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.core.ParameterizedTypeReference;

import com.ngn.utm.manager.api.ApiConvertUtil;
import com.ngn.utm.manager.api.pfsenses.ApiAuthenticationPfsenseModel;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class CoreExchangePfsenseService{

	private  OkHttpClient getHttpClient(String resourcePath) {
		return OkhttpClientCustom.getUnsafeOkHttpClient();
	}

	public  <T> T get(String url,ApiAuthenticationPfsenseModel ApiAuthenticationPfsenseModel, ParameterizedTypeReference<T> responseType) throws Exception{
		OkHttpClient client=getHttpClient(url);

		Request request = new Request.Builder()
				.url(url)
				.method("GET", null)
				.addHeader("Authorization",ApiAuthenticationPfsenseModel.getClientId()+" "+ApiAuthenticationPfsenseModel.getClientToken())
				.build();
		Response responseEntity = client.newCall(request).execute();
		return ApiConvertUtil.jsonToModel(responseEntity.body().string(), responseType);
	}

	public  <T> T  post(String url,ApiAuthenticationPfsenseModel ApiAuthenticationPfsenseModel,Object model,ParameterizedTypeReference<T> responseType) throws Exception{
		OkHttpClient client = getHttpClient(url);
		MediaType mediaType = MediaType.get("application/json; charset=utf-8");
		String jsonBody = ApiConvertUtil.modelToJson(model);

		RequestBody requestBody = RequestBody.create(jsonBody,mediaType);
		Request request = new Request.Builder()
				.url(url)
				.post(requestBody)
				.addHeader("Authorization",ApiAuthenticationPfsenseModel.getClientId()+" "+ApiAuthenticationPfsenseModel.getClientToken())
				.build();
		Response responseEntity = client.newCall(request).execute();
		return ApiConvertUtil.jsonToModel(responseEntity.body().string(),responseType);
	}

	public  <T> T postUseFormData (String url,ApiAuthenticationPfsenseModel ApiAuthenticationPfsenseModel,Object object,ParameterizedTypeReference<T> responseType) throws Exception{
		OkHttpClient okHttpClient = OkhttpClientCustom.getUnsafeOkHttpClient();
		Map<String, Object> map = ApiConvertUtil.modelToMap(object);
		
		if(map == null) {
			map = new HashMap<String, Object>();
		}
		
		JSONObject jsonObject = new JSONObject(map);;
		
		RequestBody requestBody = new RequestBody() {
			@Override
			public MediaType contentType() {
				return MediaType.parse("application/x-www-form-urlencoded");
			}

			@Override
			public void writeTo(BufferedSink sink) throws IOException {
				sink.write(jsonObject.toString().getBytes());
			}
		};

		Request request = new Request.Builder()
				.url(url)
				.addHeader("Authorization",ApiAuthenticationPfsenseModel.getClientId()+" "+ApiAuthenticationPfsenseModel.getClientToken())
				.post(requestBody)
				.build();
		Response responseEntity = okHttpClient.newCall(request).execute();
		return ApiConvertUtil.jsonToModel(responseEntity.body().string(), responseType);
	}

	public  <T> T put(String url,ApiAuthenticationPfsenseModel ApiAuthenticationPfsenseModel,Object model,ParameterizedTypeReference<T> responseType) throws Exception{
		OkHttpClient client = getHttpClient(url);
		MediaType mediaType = MediaType.get("application/json; charset=utf-8");
		String jsonBody = ApiConvertUtil.modelToJson(model);

		RequestBody requestBody = RequestBody.create(jsonBody,mediaType);
		Request request = new Request.Builder()
				.url(url)
				.put(requestBody)
				.addHeader("Authorization",ApiAuthenticationPfsenseModel.getClientId()+" "+ApiAuthenticationPfsenseModel.getClientToken())
				.build();
		Response responseEntity = client.newCall(request).execute();
		return ApiConvertUtil.jsonToModel(responseEntity.body().string(),responseType);
	}

	public  <T> T putUseFormData (String url,ApiAuthenticationPfsenseModel ApiAuthenticationPfsenseModel,Object object,ParameterizedTypeReference<T> responseType) throws Exception{
		OkHttpClient okHttpClient = OkhttpClientCustom.getUnsafeOkHttpClient();
		Map<String, Object> map = ApiConvertUtil.modelToMap(object);
		JSONObject jsonObject = new JSONObject(map);
		RequestBody requestBody = new RequestBody(){
			@Override
			public MediaType contentType() {
				return MediaType.parse("application/x-www-form-urlencoded");
			}

			@Override
			public void writeTo(BufferedSink sink) throws IOException {
				sink.write(jsonObject.toString().getBytes());
			}
		};

		Request request = new Request.Builder()
				.url(url)
				.addHeader("Authorization",ApiAuthenticationPfsenseModel.getClientId()+" "+ApiAuthenticationPfsenseModel.getClientToken())
				.put(requestBody)
				.build();
		Response responseEntity = okHttpClient.newCall(request).execute();
		return ApiConvertUtil.jsonToModel(responseEntity.body().string(), responseType);
	}

	public  <T> T delete(String url,ApiAuthenticationPfsenseModel ApiAuthenticationPfsenseModel,ParameterizedTypeReference<T> responseType) throws Exception{
		OkHttpClient client = getHttpClient(url);


		Request request = new Request.Builder()
				.url(url)
				.delete()
				.addHeader("Authorization",ApiAuthenticationPfsenseModel.getClientId()+" "+ApiAuthenticationPfsenseModel.getClientToken())
				.build();
		Response responseEntity = client.newCall(request).execute();
		return ApiConvertUtil.jsonToModel(responseEntity.body().string(), responseType);

	}
}
