package com.ngn.utm.manager.service;

import java.util.concurrent.TimeUnit;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

public class OkhttpClientCustom {
	public static OkHttpClient getClient() {
		OkHttpClient client=new OkHttpClient().newBuilder().connectTimeout(5,TimeUnit.SECONDS).readTimeout(10,TimeUnit.SECONDS).build();
		return client;
	}

	public static OkHttpClient getUnsafeOkHttpClient(){
		try {
			TrustManager[] trustAllCertificates = new TrustManager[] {
					new X509TrustManager() {

						@Override
						public X509Certificate[] getAcceptedIssuers() {
							return new X509Certificate[]{};
						}

						@Override
						public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {

						}

						@Override
						public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {

						}
					}
			};

			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, trustAllCertificates, new SecureRandom());
			SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

			OkHttpClient.Builder builder = new OkHttpClient.Builder();
			builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCertificates[0]);
			builder.hostnameVerifier((hostname, session) -> true);

			return builder.build();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
