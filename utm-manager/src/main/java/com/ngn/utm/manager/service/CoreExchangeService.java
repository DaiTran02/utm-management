package com.ngn.utm.manager.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;

public class CoreExchangeService extends CoreExchangeAbstract implements CoreExchangeInterface{

	public CoreExchangeService(String baseUrl) {
		super(baseUrl);
	}

	@Override
	public <R> HttpEntity<String> securityHeaders(String token, String body) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity<String>(body,headers);
	}

	@Override
	public <R> HttpEntity<MultiValueMap<String, Object>> securityHeadersMutiValueMap(String token,
			LinkedMultiValueMap<String, Object> mutiValueMap) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		return new HttpEntity<MultiValueMap<String,Object>>(mutiValueMap,headers);
	}

	@Override
	public <T> T get(String path, ParameterizedTypeReference<T> responseType) {
		try {
			ResponseEntity<T> responseEntity = getRestTemplate().exchange(getBaseUrl()+path,HttpMethod.GET,securityHeaders(getTokenAccess(), ""),responseType);
			return responseEntity.getBody();
		} catch (HttpClientErrorException e) {
			e.printStackTrace();
			return e.getResponseBodyAs(responseType);
		}
	}

	@Override
	public <T> T pagedGet(String path, ParameterizedTypeReference<T> responseType, String beforeOrAfter, Integer skip, Integer limit) {
		path += "?"+beforeOrAfter+"&skip="+skip+"&limit="+limit;
		return get(path, responseType);
	}

	@Override
	public <T> List<T> getAsList(String path, ParameterizedTypeReference<T[]> responseType) {
		T[] result = get(path, responseType);
		return result == null ? Collections.emptyList() : Arrays.asList(result);
	}

	@Override
	public <T> List<T> pagedGetAsList(String path, ParameterizedTypeReference<T[]> responseType, String beforeOrAfter, Integer skip, Integer limit) {
		 T[] result = pagedGet(path, responseType, beforeOrAfter, skip, limit );
	        return result == null ? Collections.emptyList() : Arrays.asList(result);
	}

	public <T, R> T post(String path, ParameterizedTypeReference<T> responseType, R jsonObject) {
		String jsonBody = toJson(jsonObject);
		try {
			ResponseEntity<T> response = getRestTemplate().exchange(getBaseUrl()+path,HttpMethod.POST,securityHeaders(getTokenAccess(), jsonBody), responseType);
			return response.getBody();
		} catch (HttpClientErrorException e) {
			e.printStackTrace();
			return e.getResponseBodyAs(responseType);
		}
	}

	@Override
	public <T, R> T postFile(String path, ParameterizedTypeReference<T> responseType, R mapObject) throws IOException {
		try {
			LinkedMultiValueMap<String, Object> mutiValueMap = toMutiValueMap(mapObject);
			ResponseEntity<T>  response = getRestTemplate().exchange(getBaseUrl()+path,HttpMethod.POST, securityHeadersMutiValueMap(getTokenAccess(), mutiValueMap),responseType);
			return response.getBody();
		} catch (HttpClientErrorException e) {
			throw new IOException();
		} catch (IllegalAccessException ex) {
			throw new IOException();
		}
	}

	@Override
	public <T, R> T put(String path, ParameterizedTypeReference<T> responseType, R jsonObject){
		String jsonBody = toJson(jsonObject);
		try {
			ResponseEntity<T> response = getRestTemplate().exchange(getBaseUrl()+path,HttpMethod.PUT,securityHeaders(getTokenAccess(), jsonBody), responseType);
			return response.getBody();
		} catch (HttpClientErrorException e) {
			e.printStackTrace();
			return e.getResponseBodyAs(responseType);
		}
	}

	@Override
	public <T> T delete(String path, ParameterizedTypeReference<T> responseType) {
		try {
			ResponseEntity<T> response = getRestTemplate().exchange(getBaseUrl()+path,HttpMethod.DELETE,securityHeaders(getTokenAccess(), ""),responseType);
			return response.getBody();
		} catch (HttpClientErrorException e) {
			e.printStackTrace();
			return e.getResponseBodyAs(responseType);
		}
	}
	
	@Override
	public <T, R> T authen(String path, ParameterizedTypeReference<T> responseType, R jsonObject) {
		String jsonBody = toJson(jsonObject);
		ResponseEntity<T> response = getRestTemplate().exchange(getBaseUrl()+path,HttpMethod.POST,securityHeaders("",jsonBody), responseType);
		return response.getBody();
	}

}
